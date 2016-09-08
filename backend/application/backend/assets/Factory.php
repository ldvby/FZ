<?php

namespace backend\assets;

use Yii;
use yii\db\Exception;
use \Firebase\FirebaseLib;

class Factory {

    public static function getFirebaseDb(){
        static $fireDB = null;
        if(is_null($fireDB)) {
            $settings = Yii::$app->params["firebaseDB"];
            $fireDB = new FirebaseLib($settings["url"], $settings["token"]);
            if(empty($fireDB)) {
                throw new Exception("Cann't connect to database");
            }
        }
        return $fireDB;
    }

}