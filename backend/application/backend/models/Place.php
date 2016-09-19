<?php
namespace backend\models;


use backend\assets\Factory;
use Yii;

interface iPlace {
    public function save();
    public function loadById($_id);
}

/**
 * Place model
 */
class Place extends \yii\base\Model implements iPlace
{
    use FoodzenModelTrait;

    const COLLECTION_NAME = "place";
    const COLLECTION_ID_LENGTH = 32;

    const SCENARIO_CREATE = "Create";
    const SCENARIO_UPDATE = "Update";

    public $_id;
    public $nameNative;
    public $nameTranslit;
    public $countryNative;
    public $countryTranslit;
    public $cityNative;
    public $cityTranslit;
    public $addressNative;
    public $addressTranslit;
    public $geo_instagram;
    public $geo_coordinates;
    public $dateCreate;
    public $dateUpdate;
    public $tsUpdate;

    public function scenarios(){
        return [
            self::SCENARIO_CREATE => $this->getSafeAttributes(),
            self::SCENARIO_UPDATE => $this->getSafeAttributes(),
        ];
    }

    public function rules(){
        return [
            [$this->getSafeAttributes(),  'required'],

            ['_id', 'string', 'max' => static::COLLECTION_ID_LENGTH, 'min' => static::COLLECTION_ID_LENGTH, "except"=>self::SCENARIO_CREATE],
            ['nameNative', 'string', 'max' => 50],
            ['nameTranslit', 'string', 'max' => 50],
            ['countryNative', 'string', 'max' => 50],
            ['countryTranslit', 'string', 'max' => 50],
            ['cityNative', 'string', 'max' => 50],
            ['cityTranslit', 'string', 'max' => 50],
            ['addressNative', 'string', 'max' => 100],
            ['addressTranslit', 'string', 'max' => 100],
            ['geo_instagram', function ($attribute, $params) {
                $attr=$this->$attribute;
                if(
                    !is_array($attr)
                    || count(array_filter ( $attr, "is_int" )) != count($attr)
                ){
                    $this->addError($attribute, 'Invalid format. Expected array of integers');
                }
            }],
            ['geo_coordinates', function ($attribute, $params) {
                $attr=$this->$attribute;
                if(
                    !is_array($attr)
                    || !isset($attr["lat"], $attr["lng"])
                    || !is_double($attr["lat"])
                    || !is_double($attr["lng"])
                ){
                    $this->addError($attribute, 'Invalid format. Expected [lat:{double}, lng:{double}]');
                }
            }],
        ];
    }


    public function save(){
        $this->initPrivateAttributes();
        switch ($this->scenario){
            case self::SCENARIO_CREATE:
                $saveMethod="set";
                break;

            case self::SCENARIO_UPDATE:
                $saveMethod="update";
                break;

            default:
                $this->addError("scenario", "Unknown scenario ".$this->scenario);
                return false;
                break;
        }


       if($this->validate()){
            $fireDb = Factory::getFirebaseDb();
            $path = $this->getDbPath() . $this->_id;
            $fireDb->$saveMethod($path, $this->attributes);
            return true;
       }
       return false;
    }


    public function loadById($_id){
        $this->setScenario(self::SCENARIO_UPDATE);
        $this->_id = $_id;

        $fireDb = Factory::getFirebaseDb();
        $path = $this->getDbPath() . $this->_id;
        $dataJson = $fireDb->get($path);
        $data = json_decode($dataJson, true);
        if(empty($data)) {
            return false;
        }
        $this->attributes = $data;
        foreach ($this->getUnsafeAttributes() as $attrName){
            if(isset($data[$attrName])) $this->$attrName = $data[$attrName];
        }
        return true;
    }

     /**
     * Инициализирует частные аттрибуты, которые нельзя выставить из вне
     */
   private function initPrivateAttributes(){
       $this->tsUpdate = $this->getUtcTime();
       $this->dateUpdate = date("Y-m-d h:i:s", $this->tsUpdate);
       switch ($this->scenario){
           case self::SCENARIO_CREATE:
               $this->generateId();
               $this->dateCreate = $this->dateUpdate;
               break;

           case self::SCENARIO_UPDATE:;
               break;

       }
   }

   private function getSafeAttributes(){
       return ['nameNative',
                'nameTranslit',
                'countryNative',
                'countryTranslit',
                'cityNative',
                'cityTranslit',
                'addressNative',
                'addressTranslit',
                'geo_instagram',
                'geo_coordinates'];
   }

    private function getUnsafeAttributes(){
        return ['dateCreate', 'dateUpdate', 'tsUpdate'];
    }

}
