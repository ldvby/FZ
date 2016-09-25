<?php

namespace backend\assets;

use Yii;
use yii\db\Exception;
use \Firebase\FirebaseLib;
use yii\db\mssql\PDO;

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

    public static function getSphinxDb(){
        static $sphinxDB = null;
        if(is_null($sphinxDB)) {
            $settings = Yii::$app->params["sphinx"];
            $dsn=sprintf("mysql:host=%s;port=%s;charset=utf8;", $settings["host"], $settings["port"]);
            $sphinxDB = new PDO($dsn);
            $sphinxDB->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
        }
        return $sphinxDB;
    }

}