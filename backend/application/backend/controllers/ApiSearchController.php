<?php
namespace backend\controllers;

use backend\assets\Factory;
use backend\models\Place;
use backend\models\SearchPlace;
use Yii;
use yii\rest\Controller;
use yii\filters\auth\HttpBasicAuth;


/**
 * api-search controller
 */
class ApiSearchController extends Controller
{
    use ApiControllerTrait;

    public function actionPlace($query, $lat, $lng, $radius, $limit, $offset, $orderBy="weight") {
        $searchPlace = new SearchPlace([
            "query" => $query,
            "lat" => $lat,
            "lng" => $lng,
            "radius" => $radius,
            "limit" => $limit,
            "offset" => $offset,
            "orderBy" => $orderBy
        ]);
        return $searchPlace->find();
    }

}
