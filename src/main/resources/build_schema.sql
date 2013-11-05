/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

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

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
SET=latin1 AUTO_INCREMENT=1 ;