<?php
namespace backend\models;


trait FoodzenModelTrait {

    /**
     * Генерирует уникальный id для записи.
     *
     */
    protected function generateId(){
        //$this->_id = \Yii::$app->security->generateRandomString(static::COLLECTION_ID_LENGTH);//(string) new \MongoId();
        $this->_id = $this->generate_id_hex();
    }

    protected function getDbPath(){
        return \Yii::$app->params["firebaseDB"]["path"] . "/" . static::COLLECTION_NAME . "/";
    }

    protected function getUtcTime(){
        return time() - date("z");
    }

    /**
     * Generate an ID, constructed using:
     *   a 4-byte value representing the seconds since the Unix epoch,
     *   a 3-byte machine identifier,
     *   a 2-byte process id, and
     *   a 3-byte counter, starting with a random value.
     * Just like a MongoId string.
     *
     * @link http://www.luokr.com/p/16
     * @link http://docs.mongodb.org/manual/reference/object-id/
     * @return string 24 hexidecimal characters
     */
    private function  generate_id_hex()
    {
        static $i = 0;
        $i OR $i = mt_rand(1, 0x7FFFFF);

        return sprintf("%08x%06x%04x%06x",
            /* 4-byte value representing the seconds since the Unix epoch. */
            $this->getUtcTime() & 0xFFFFFFFF,

            /* 3-byte machine identifier.
             *
             * On windows, the max length is 256. Linux doesn't have a limit, but it
             * will fill in the first 256 chars of hostname even if the actual
             * hostname is longer.
             *
             * From the GNU manual:
             * gethostname stores the beginning of the host name in name even if the
             * host name won't entirely fit. For some purposes, a truncated host name
             * is good enough. If it is, you can ignore the error code.
             *
             * crc32 will be better than Times33. */
            crc32(substr((string)gethostname(), 0, 256)) >> 8 & 0xFFFFFF,

            /* 2-byte process id. */
            getmypid() & 0xFFFF,

            /* 3-byte counter, starting with a random value. */
            $i = $i > 0xFFFFFE ? 1 : $i + 1
        );
    }
}