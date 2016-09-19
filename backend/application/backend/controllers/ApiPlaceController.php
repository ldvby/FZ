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
        $time = microtime(true);

        $place = new Place();
        $place->setScenario(Place::SCENARIO_CREATE);
        $place->nameNative = "Бэкенд имя";
        $place->nameTranslit = "Backend name";
        $place->countryNative = "Беларусь";
        $place->countryTranslit = "Belarus";
        $place->cityNative = "Минск";
        $place->cityTranslit = "Minsk";
        $place->addressNative = "Независимости 157, 278";
        $place->addressTranslit = "Nezavisimosti 157, 278";
        $place->geo_instagram = [244454397];
        $place->geo_coordinates = ["lat"=>53.935810, "lng"=>27.651270];


        if(!$place->save()) {
            return $place->errors;
        }
        return "OK";
    }

    public function actionUpdate(){
        $placeId= "_qCMcPPvAqxbw1J2f8RYvUo6kKtolOCQ";
        $place = new Place();
        if(!$place->loadById($placeId)){
            return "PlaceId: {$placeId} not found";
        }
        $place->save();
        return $place->attributes;

    }

    public function actionGetUpdated(){
        $placeId= "_qCMcPPvAqxbw1J2f8RYvUo6kKtolOCQ";
        $place = new Place();
        if(!$place->loadById($placeId)){
            return "PlaceId: {$placeId} not found";
        }
        $place->save();
        return $place->attributes;

    }

}
