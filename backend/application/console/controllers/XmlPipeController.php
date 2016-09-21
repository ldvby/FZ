<?php

namespace console\controllers;

use backend\assets\Factory;
use backend\models\Place;
use yii\console\Controller;

/**
 * Базовый класс для генерациии XmlPipe
 */
abstract class XmlPipeController extends Controller {
    private $fileHandle;
    private $docId=1;

    public abstract function actionIndex();
    protected abstract function main();
    protected abstract function fields();
    protected abstract function attrs();


//    public function actionIndex() {
//        echo "Error! Use specific Xml class pipe";
//    }

    protected function writeData($sData) {
        fwrite($this->fileHandle, $sData);
    }

    protected function runPipe() {
        $fileName = \Yii::$app->params["cacheDir"]."xmlpipe_".get_class($this).".xml";
        $this->fileHandle = fopen($fileName, 'c');
        if (flock($this->fileHandle, LOCK_EX | LOCK_NB) ) {
            ftruncate($this->fileHandle, 0);
            $this->writeData('<?xml version="1.0" encoding="utf-8"?>' . PHP_EOL);
            $this->writeData('<sphinx:docset  xmlns:sphinx=\'bogus\'>' . PHP_EOL);
            $this->declareSchema();
            $this->main();
            $this->writeData('</sphinx:docset>');
            fflush($this->fileHandle);
            flock($this->fileHandle, LOCK_UN);
        }
        fclose($this->fileHandle);

        // Отдаем контент файла
        $fh = fopen($fileName, 'rb');
        flock($fh, LOCK_SH);
        while (!feof($fh)) {
            print fread($fh, 8192);
        }
        flock($fh, LOCK_UN);
        fclose($fh);
    }

    private function declareSchema(){
        $schema = '<sphinx:schema>';
        foreach ($this->fields() as $field){
            $schema.='<sphinx:field name="'.$this->toFieldName($field).'"/>';
        }
        foreach ($this->attrs() as $attrName => $attr){
            $default = isset($attr["default"]) ? (' default="'.$attr["default"].'"') : "";
            $schema.='<sphinx:attr name="'.$this->toFieldName($attrName).'" type="'.$attr["type"].'"'.$default.'/>';
        }
        $schema .= '</sphinx:schema>';
        $this->writeData($schema);
    }

    /**
     * Конвертирыет название поля в валидное название поля для сфинкса
     * @param $name
     */
    private function toFieldName($name){
        $validname = preg_replace("/([a-z0-9])([A-Z])/", "$1_$2", $name);
        return strtolower($validname);
    }

    /**
     * Записывает в pipe значение  item
     * @param $data
     */
    protected function declareItem($data){
        $item = '<sphinx:document id="'.($this->docId++).'">';
        foreach ($data as $k => $v){
            $fieldName = $this->toFieldName($k);
            $item.="<{$fieldName}>".htmlspecialchars($v)."</{$fieldName}>";
        }
        $item .= '</sphinx:document>';
        $this->writeData($item);
    }
}