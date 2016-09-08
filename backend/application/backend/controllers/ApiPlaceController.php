<?php
namespace backend\controllers;

use backend\models\Place;
use Yii;
use yii\rest\Controller;
use yii\filters\auth\HttpBasicAuth;


/**
 * api-place controller
 */
class ApiPlaceController extends Controller
{
    use ApiControllerTrait;

    public function actionCreate($name)
    {
        return ["name"=>$name, "result"=>"ok"];
    }

    public function actionTest(){
        $time = microtime(true);

        $place = new Place();
        $place->nameNative = "Бэкенд имя";
        $place->nameTranslit = "Backend name";

        return $place->save();

        return [$data,(microtime(true) - $time)];


    }


}
