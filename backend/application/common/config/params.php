<?php
return [
    'adminEmail' => 'admin@example.com',
    'supportEmail' => 'support@example.com',
    'user.passwordResetTokenExpire' => 3600,

    "firebaseDB" => [
        "token"=>"rJocorXTbBw1T3g2V6PuKfcaUnni3nLXRX8kuqZM",
        "url"=>"https://foodzen-22a66.firebaseio.com/",
        "path"=>""
    ],

    "sphinx" => [
        "host"=>"127.0.0.1",
        "port"=>"9306",
    ],

    "cacheDir" => realpath(__DIR__."/../../../cache/"),
];
