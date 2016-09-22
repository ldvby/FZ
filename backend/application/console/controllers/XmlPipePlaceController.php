<?php

namespace console\controllers;

use backend\assets\Factory;
use backend\models\Place;
use yii\base\Exception;
use yii\console\Controller;

/**
 * Контроллер генерит Xml Pipes для индекса сфинкса
 */
class XmlPipePlaceController extends XmlPipeController  {

    public function actionIndex() {
        $this->runPipe();
    }

    protected function main(){
        $fireDb = Factory::getFirebaseDb();
        $path = \Yii::$app->params["firebaseDB"]["path"] . "/" . Place::COLLECTION_NAME . "/";

        $pipeItemFields = array_merge($this->fields(), array_keys($this->attrs()));
        $blankItem = array_combine($pipeItemFields, array_fill(0, count($pipeItemFields), ""));

        $stepSize = 500;
        $lastPlaceId = null;
        do {
//            $placesJson = $fireDb->get($path, ["orderBy"=>'"tsUpdate"', "limitToFirst"=>1, "startAt"=>1474062965]);
            $aQuery =["orderBy"=>'"$key"', "limitToFirst"=>$stepSize];
            if(!is_null($lastPlaceId)) {
                $aQuery["startAt"]= '"'.$lastPlaceId.'"';
            }
            $placesJson = $fireDb->get($path, $aQuery);
            $places = json_decode($placesJson, true);
            if(empty($places)) break;
            if(!is_array($places)) {
                throw new Exception($places);
            }

            foreach ($places as $place){
                $this->declareItem(array_intersect_key($place, $blankItem));
                $lastPlaceId = $place["_id"];
            }
        } while (count($places)>=$stepSize);
    }

    protected function fields(){
        static $fields = ["nameNative", "nameTranslit", "countryNative", "countryTranslit",
            "cityNative", "cityTranslit", "addressNative", "addressTranslit"];
        return $fields;
    }

    protected function attrs(){
        static $attrs = [
          "_id"=>["type"=>"string"] ,
          "lng"=>["type"=>"float", "default"=>"0"],
          "lat"=>["type"=>"float", "default"=>"0"],
        ];
        return $attrs;
    }




    public function actionMail($to) {
        echo "Sending mail to " . $to;
    }

}