--
-- Database: `test`
--

-- --------------------------------------------------------

--
-- Table structure for table `sss_tweet_table`
--
CREATE TABLE IF NOT EXISTS `sss_tweet_table` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tweet_id` bigint(20) NOT NULL,
  `tweet` varchar(1024) NOT NULL,
  `screenname` varchar(256) NOT NULL,
  `timestamp` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tweet_id` (`tweet_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;
