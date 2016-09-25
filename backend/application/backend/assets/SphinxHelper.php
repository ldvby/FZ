<?php
namespace backend\assets;

class SphinxHelper
{
	/* индексы: */
	const INDEX_COMPANIES = 'places';

	public static function escapeString($string)
	{
		$from = array('\\', '(', ')', '|', '-', '!', '@', '~', '"', '&', '/', '^', '$', '=');
		$to = array('\\\\', '\(', '\)', '\|', '\-', '\!', '\@', '\~', '\"', '\&', '\\\/', '\^', '\$', '\=');
		return str_replace($from, $to, $string);
	}
}
