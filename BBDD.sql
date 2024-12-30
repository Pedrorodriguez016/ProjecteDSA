-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         11.6.2-MariaDB - mariadb.org binary distribution
-- SO del servidor:              Win64
-- HeidiSQL Versión:             12.8.0.6908
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Volcando estructura de base de datos para game
CREATE DATABASE IF NOT EXISTS `game` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_uca1400_ai_ci */;
USE `game`;

-- Volcando estructura para tabla game.faq
CREATE TABLE IF NOT EXISTS `faq` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sender` int(11) NOT NULL,
  `date` date NOT NULL,
  `question` mediumtext NOT NULL,
  `answer` mediumtext NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `question` (`question`) USING HASH,
  KEY `user` (`sender`),
  CONSTRAINT `user` FOREIGN KEY (`sender`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- Volcando datos para la tabla game.faq: ~2 rows (aproximadamente)
DELETE FROM `faq`;
INSERT INTO `faq` (`id`, `sender`, `date`, `question`, `answer`) VALUES
	(1, 2, '2024-12-20', '¿Cómo abrir el juego?', 'Dándole al icono del juego.'),
	(2, 1, '2014-10-25', '¿Cuándo saldrá el juego en versión final?', 'Próximamente, a mediados de enero del 2025.');

-- Volcando estructura para tabla game.inventory
CREATE TABLE IF NOT EXISTS `inventory` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user` int(11) NOT NULL,
  `item` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `item_id` (`item`),
  KEY `user_id` (`user`),
  CONSTRAINT `item_id` FOREIGN KEY (`item`) REFERENCES `item` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `user_id` FOREIGN KEY (`user`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- Volcando datos para la tabla game.inventory: ~1 rows (aproximadamente)
DELETE FROM `inventory`;
INSERT INTO `inventory` (`id`, `user`, `item`, `quantity`) VALUES
	(1, 1, 1, 3);

-- Volcando estructura para tabla game.item
CREATE TABLE IF NOT EXISTS `item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` enum('KNIFE','SHIELD','SWORD','POTION','ARMOR') NOT NULL,
  `value` int(11) NOT NULL DEFAULT 0,
  `description` varchar(5000) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- Volcando datos para la tabla game.item: ~2 rows (aproximadamente)
DELETE FROM `item`;
INSERT INTO `item` (`id`, `type`, `value`, `description`) VALUES
	(1, 'KNIFE', 145, 'Un cuchillo sin más. Te ayudará en lo más básico, pero no esperes grandes progresos con esto...'),
	(2, 'SHIELD', 540, 'Este escudo de madera fue usado por un legendario héroe que solía ir vestido de verde. Parece un escudo normal y corriente, pero no lo subestimes.');

-- Volcando estructura para tabla game.message
CREATE TABLE IF NOT EXISTS `message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sender` int(11) NOT NULL,
  `thread` int(11) NOT NULL,
  `message` longtext NOT NULL,
  `date` date NOT NULL,
  `parent_message` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `sender_id` (`sender`),
  KEY `parent_message` (`parent_message`),
  KEY `thread_id` (`thread`),
  CONSTRAINT `parent_message` FOREIGN KEY (`parent_message`) REFERENCES `message` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `sender_id` FOREIGN KEY (`sender`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `thread_id` FOREIGN KEY (`thread`) REFERENCES `thread` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- Volcando datos para la tabla game.message: ~2 rows (aproximadamente)
DELETE FROM `message`;
INSERT INTO `message` (`id`, `sender`, `thread`, `message`, `date`, `parent_message`) VALUES
	(1, 1, 2, 'test', '2024-10-05', NULL),
	(2, 45, 2, 'yay', '2024-10-06', 1);

-- Volcando estructura para tabla game.thread
CREATE TABLE IF NOT EXISTS `thread` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` text NOT NULL,
  `date` date NOT NULL,
  `creator` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `creator_id` (`creator`),
  CONSTRAINT `creator_id` FOREIGN KEY (`creator`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- Volcando datos para la tabla game.thread: ~2 rows (aproximadamente)
DELETE FROM `thread`;
INSERT INTO `thread` (`id`, `title`, `date`, `creator`) VALUES
	(1, 'wow', '2024-10-05', 1),
	(2, 'wow', '2024-10-05', 1);

-- Volcando estructura para tabla game.user
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL DEFAULT '0',
  `password` varchar(255) NOT NULL DEFAULT '0',
  `email` varchar(100) NOT NULL DEFAULT '0',
  `money` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- Volcando datos para la tabla game.user: ~3 rows (aproximadamente)
DELETE FROM `user`;
INSERT INTO `user` (`id`, `username`, `password`, `email`, `money`) VALUES
	(1, '4', '4', '6@2.com', 260),
	(2, '15161651', '4', '6161650@2.com', 0),
	(45, 'testuser', 'testpassword', 'testemail@testemail.com', 1542);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
