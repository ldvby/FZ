<?php
namespace backend\models;

use backend\assets\Factory;

interface iPhoto {
    public function save();
    public function loadById($_id);
}

/**
 * Place model
 */
class Photo extends \yii\base\Model implements iPhoto
{
    use FoodzenModelTrait;

    const COLLECTION_NAME = "photo";
    const COLLECTION_ID_LENGTH = 32;

    const SCENARIO_CREATE = "Create";
    const SCENARIO_UPDATE = "Update";
    const SCENARIO_DELETE = "Delete";

    public $_id;
    public $visitorId;
    public $placeId;
    public $review;
    public $raiting;
    public $geo;
    public $dateCreate;
    public $dateUpdate;

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
            ['visitorId', 'string', 'max' => static::COLLECTION_ID_LENGTH, 'min' => static::COLLECTION_ID_LENGTH],
            ['placeId', 'string', 'max' => Place::COLLECTION_ID_LENGTH, 'min' => Place::COLLECTION_ID_LENGTH],
            ['review', 'string', 'max' => 4000],
            ['raiting', 'numerical'],
            ['geo', function ($attribute, $params) {
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
            case self::SCENARIO_DELETE:
                $saveMethod="delete";
                break;

            default:
                $this->addError("scenario", "Unknown scenario ".$this->scenario);
                return false;
                break;
        }


       if($this->validate()){
            $fireDb = Factory::getFirebaseDb();
            $path = $this->getDbPath() . $this->_id;
            if($this->scenario == self::SCENARIO_DELETE){
                $fireDb->delete($path);
            }
            else{
                $fireDb->$saveMethod($path, $this->attributes);
            }
            return true;
       }
       return false;
    }


    public function loadById($_id){
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
       $this->dateUpdate = date("Y-m-d h:i:s");
       switch ($this->scenario){
           case self::SCENARIO_CREATE:
               $this->generateId();
               $this->dateCreate = date("Y-m-d h:i:s");
               break;

           case self::SCENARIO_UPDATE:;
               break;

       }
   }

   private function getSafeAttributes(){
       return ['visitorId',
                'placeId',
                'review',
                'raiting',
                'geo'];
   }

    private function getUnsafeAttributes(){
        return ['dateCreate', 'dateUpdate'];
    }

}
