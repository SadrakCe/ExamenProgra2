
CREATE DATABASE IF NOT EXISTS `sistema_agrario` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `sistema_agrario`;


CREATE TABLE IF NOT EXISTS `parcelas` (
  `codigo` VARCHAR(50) NOT NULL,
  `nombre` VARCHAR(100) NOT NULL,
  `ubicacion` VARCHAR(150) NOT NULL,
  `area` DOUBLE NOT NULL,
  `tipo_suelo` VARCHAR(50) NOT NULL,
  `estado` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE IF NOT EXISTS `cultivos` (
  `codigo` VARCHAR(50) NOT NULL,
  `nombre` VARCHAR(100) NOT NULL,
  `variedad` VARCHAR(100) NOT NULL,
  `fecha_siembra` DATE NOT NULL,
  `tipo_cultivo` VARCHAR(20) NOT NULL, 
  `dato_especifico` INT NOT NULL,      
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE IF NOT EXISTS `responsables` (
  `identificacion` VARCHAR(50) NOT NULL,
  `nombre` VARCHAR(100) NOT NULL,
  `correo` VARCHAR(100) NOT NULL,
  `telefono` VARCHAR(50) NOT NULL,
  `tipo_responsable` VARCHAR(20) NOT NULL, 
  `dato_especifico` VARCHAR(150) NOT NULL,  
  PRIMARY KEY (`identificacion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE IF NOT EXISTS `labores_agricolas` (
  `codigo_labor` INT NOT NULL AUTO_INCREMENT, 
  `codigo_parcela` VARCHAR(50) NOT NULL,
  `codigo_cultivo` VARCHAR(50) NOT NULL,
  `id_responsable` VARCHAR(50) NOT NULL,
  `fecha_labor` DATE NOT NULL,
  `tipo_labor` VARCHAR(50) NOT NULL,
  `descripcion` TEXT,
  `costo` DOUBLE NOT NULL,
  PRIMARY KEY (`codigo_labor`),
  CONSTRAINT `fk_labor_parcela` FOREIGN KEY (`codigo_parcela`) REFERENCES `parcelas` (`codigo`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_labor_cultivo` FOREIGN KEY (`codigo_cultivo`) REFERENCES `cultivos` (`codigo`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_labor_responsable` FOREIGN KEY (`id_responsable`) REFERENCES `responsables` (`identificacion`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;