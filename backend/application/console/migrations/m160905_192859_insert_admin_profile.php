<?php

use yii\db\Migration;

class m160905_192859_insert_admin_profile extends Migration
{

    const USER_NAME = "admin";
    const USER_PASS = "rtfgvb";
    const USER_EMAIL = "pavel.krulov@gmail.com";

    public function up()
    {
        $this->insert(\common\models\User::tableName(),[
            "username"=>self::USER_NAME,
            "auth_key"=>\Yii::$app->security->generateRandomString(32),
            "password_hash"=>\Yii::$app->security->generatePasswordHash(self::USER_PASS),
            "password_reset_token"=>\Yii::$app->security->generateRandomString(128),
            "email"=>self::USER_EMAIL,
            "created_at"=>time(),
            "updated_at"=>time()
        ]);

    }

    public function down()
    {
//        echo "m160905_192859_insert_admin_profile cannot be reverted.\n";
//        return false;


        return $this->delete(\common\models\User::tableName(), ["username"=>self::USER_NAME]);

    }

    /*
    // Use safeUp/safeDown to run migration code within a transaction
    public function safeUp()
    {
    }

    public function safeDown()
    {
    }
    */
}
