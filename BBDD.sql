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

-- Volcando datos para la tabla game.inventory: ~1 rows (aproximadamente)
INSERT INTO `inventory` (`id`, `user`, `item`, `quantity`) VALUES
	(1, 1, 1, 2);

-- Volcando datos para la tabla game.item: ~2 rows (aproximadamente)
INSERT INTO `item` (`id`, `type`, `value`, `description`) VALUES
	(1, 'KNIFE', 145, 'Un cuchillo sin más. Te ayudará en lo más básico, pero no esperes grandes progresos con esto...'),
	(2, 'SHIELD', 540, 'Este escudo de madera fue usado por un legendario héroe que solía ir vestido de verde. Parece un escudo normal y corriente, pero no lo subestimes.');

-- Volcando datos para la tabla game.user: ~1 rows (aproximadamente)
INSERT INTO `user` (`id`, `username`, `password`, `email`, `money`) VALUES
	(1, '4', '4', '6@2.com', 0),
	(2, '15161651', '4', '6161650@2.com', 0);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
