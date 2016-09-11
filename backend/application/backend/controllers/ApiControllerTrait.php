<?php
namespace backend\controllers;

use Yii;
use yii\filters\auth\HttpBasicAuth;

/**
 * Class ApiControllerTrait
 * @package backend\controllers
 *
 * Общие настройки для API методов + аутентификация.
 */
trait ApiControllerTrait {

    public function init()
    {
        parent::init();
        Yii::$app->user->identityClass = "common\models\UserApi";
        Yii::$app->user->enableSession = false;
    }


    public function behaviors()
    {
        $behaviors = parent::behaviors();

        $behaviors['authenticator'] = [
            'class' => HttpBasicAuth::className(),
            'auth' => (function ($username, $password) {
                $credentials = Yii::$app->params["apiUsers"];
                if(isset($credentials[$username]) && $credentials[$username]==$password){
                    return new \common\models\UserApi();
                }
                return null;
            })
        ];
        return $behaviors;
    }
}