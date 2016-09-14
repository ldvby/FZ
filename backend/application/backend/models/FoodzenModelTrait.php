<?php
namespace backend\models;


trait FoodzenModelTrait {

    /**
     * Генерирует уникальный id для записи.
     *
     */
    protected function generateId(){
        $this->_id = \Yii::$app->security->generateRandomString(static::COLLECTION_ID_LENGTH);//(string) new \MongoId();
    }

    protected function getDbPath(){
        return \Yii::$app->params["firebaseDB"]["path"] . "/" . static::COLLECTION_NAME . "/";
    }

}