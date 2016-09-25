<?php
namespace backend\models;


use backend\assets\Factory;
use backend\assets\SphinxHelper;
use Yii;

interface iSearchPlace {
    public function find();
    public function findByCoordinates();
}

/**
 * Class SearchPlace
 * @package backend\models
 * @property  string $query - search query
 * @property  float $lat - latitude
 * @property  float $lng - longitude
 * @property  int $limit - limit results
 * @property  int $offset - offset results
 * @property  int $radius - max company distance with respect to the point (meters)
 * @property  const $orderBy - sort method weight|distance
 *
 */
class SearchPlace extends SearchBase implements iSearchPlace
{
    const SPHINX_INDEX = "places";

    const ORDER_BY_WEIGHT = "weight";
    const ORDER_BY_DISTANCE = "distance";

    protected function init(){
        $this->query = $this->getParam("query", null);
        $this->limit = min($this->getParam("limit", 100), 200);
        $this->offset = $this->getParam("offset", 0);
        $this->lat = $this->getParam("lat", null);
        $this->lng = $this->getParam("lng", null);
        $this->radius = $this->getParam("radius", null);
        $this->orderBy = $this->getParam("orderBy", null);
    }



    public function find(){
        $sphinx = Factory::getSphinxDb();

        $query = sprintf('
			SELECT %1$s FROM %2$s WHERE %3$s ORDER BY %8$s LIMIT %4$d, %5$d
			OPTION ranker=%7$s, 
			field_weights=(nameNative=10, nameTranslit=8, cityNative=6, cityTranslit=6, addressNative=4, addressTranslit=4 ),
			max_matches=%6$d',
            $this->getReturnFields(),
            self::SPHINX_INDEX,
            $this->makeQuery(),
            $this->offset,
            $this->limit,
            $this->limit + $this->offset,
            $this->makeRanker(),
            $this->makeOrderBy()
        );

        $stmt = $sphinx->prepare($query);
        $stmt->execute();
        return $stmt->fetchAll(\PDO::FETCH_ASSOC);
    }

    public function findByCoordinates()
    {
        // TODO: Implement findByCoordinates() method.
    }

    private function getReturnFields($extra=[]){
        $default = ["_id", "lat", "lng", "weight() as w"];
        $default[] = $this->makeFieldDistance();
        $default = array_filter($default);
        return implode(",", array_merge($default, $extra));
    }


    private function makeQuery(){
        $parts = [
            $this->makeQueryString(),
            $this->makeQueryRadius(),
        ];
        $parts = array_filter($parts);
        return implode(" AND ", $parts);
    }

    private function makeQueryString(){
        if(empty($this->query)) return false;
        $query = SphinxHelper::escapeString($this->query);
        return "MATCH('{$query}')";

    }

    private function makeQueryRadius(){
        if (!$this->isCoordinatesOK() || empty($this->radius)) return false;
        return sprintf("distance < %d", $this->radius);
    }

    private function makeFieldDistance(){
        if (!$this->isCoordinatesOK()) return false;
        return sprintf('GEODIST(%f, %f, lat, lng, {in=degrees, out=meters}) AS distance', $this->lat, $this->lng);
    }

    private function makeRanker(){
        return !empty($this->query) ? "sph04" : "none";
    }

    private function makeOrderBy(){
        switch ($this->orderBy){
            case self::ORDER_BY_DISTANCE:
                $orderBy = "distance ASC";
                break;

            case self::ORDER_BY_WEIGHT:
            default:
                $orderBy = "w DESC";
                break;
        }
        return $orderBy;
    }

    private function isCoordinatesOK(){
        return !empty($this->lat) && !empty($this->lng);
    }
}
