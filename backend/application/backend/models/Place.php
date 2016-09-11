<?php
namespace backend\models;

use backend\assets\Factory;
use Yii;

/**
 * Place model
 */
class Place extends \yii\base\Model
{
    const COLLECTION_NAME = "place";

    const ID_LENGTH = 32;

    public $_id;
    public $nameNative;
    public $nameTranslit;

    public function rules()
    {
        return [
            // Place uniq id
            ['_id', 'required'],
            ['_id', 'string', 'max' => static::ID_LENGTH, 'min' => static::ID_LENGTH],

            // Place native name
            ['nameNative', 'required'],
            ['nameNative', 'string', 'max' => 50],

            // Place native name
            ['nameTranslit', 'required'],
            ['nameTranslit', 'string', 'max' => 50],
        ];
    }


    public function save(){
       $saveMethod="update";
       if(empty($this->_id)) {
           $this->generateId();
           $saveMethod="set";
       }

       if($this->validate()){
            $fireDb = Factory::getFirebaseDb();
            $path = $this->getDbPath() . $this->_id;
            $fireDb->$saveMethod($path, $this->attributes);
            return true;
       }
       return false;
    }

   /**
    * Генерирует уникальный id для записи.
    *
    */
   private function generateId(){
       $this->_id = \Yii::$app->security->generateRandomString(static::ID_LENGTH);//(string) new \MongoId();
   }

   private function getDbPath(){
       return \Yii::$app->params["firebaseDB"]["path"] . "/" . static::COLLECTION_NAME . "/";
   }
}
