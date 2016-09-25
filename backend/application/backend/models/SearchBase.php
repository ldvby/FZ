<?php
namespace backend\models;


use backend\assets\Factory;
use Yii;
use yii\base\Exception;


abstract class SearchBase {

    private $params=[];
    public function __construct(array $params){
        $requiredParams = $this->getRequiredParams();
        //удаляем неизвестные ключи
        $issetRequiredParams = array_intersect_key($params, $requiredParams);
        if(count($issetRequiredParams) != count($requiredParams)) {
            throw new Exception("Required params: ".implode(",", $requiredParams));
        }
        //добавляем дефолтные значения
        $this->params = $params;
        $this->init();
    }

    protected function getParam($name, $default=null){
        return isset($this->params[$name]) ? $this->params[$name] : $default;
    }

    protected function getRequiredParams(){
        return [];
    }

    /* Инициализация параметров */
    abstract protected function init();

}