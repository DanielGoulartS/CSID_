create database csid;
use csid;
-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Tempo de geração: 15-Nov-2022 às 13:32
-- Versão do servidor: 8.0.21
-- versão do PHP: 7.3.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `csid`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `equipamentos`
--

DROP TABLE IF EXISTS `equipamentos`;
CREATE TABLE IF NOT EXISTS `equipamentos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) NOT NULL,
  `quantidade` int NOT NULL,
  PRIMARY KEY (`id`)
);

-- --------------------------------------------------------

--
-- Estrutura da tabela `equipamentos_solicitados`
--

DROP TABLE IF EXISTS `equipamentos_solicitados`;
CREATE TABLE IF NOT EXISTS `equipamentos_solicitados` (
  `solicitacao` int DEFAULT NULL,
  `id` int NOT NULL,
  `quantidade` int NOT NULL
);

-- --------------------------------------------------------

--
-- Estrutura da tabela `navios`
--

DROP TABLE IF EXISTS `navios`;
CREATE TABLE IF NOT EXISTS `navios` (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `nome` varchar(40) DEFAULT NULL,
  `numero` int DEFAULT NULL,
  PRIMARY KEY (`id`)
);

-- --------------------------------------------------------

--
-- Estrutura da tabela `portos`
--

DROP TABLE IF EXISTS `portos`;
CREATE TABLE IF NOT EXISTS `portos` (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `nome` varchar(40) DEFAULT NULL,
  `ddi` int NOT NULL,
  `ddd` int NOT NULL,
  `telefone` varchar(40) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `rua` varchar(20),
  `numero` int DEFAULT NULL,
  `cidade` varchar(20),
  `estado` varchar(20),
  `pais` varchar(20),
  PRIMARY KEY (`id`)
);

-- --------------------------------------------------------

--
-- Estrutura da tabela `servicos`
--

DROP TABLE IF EXISTS `servicos`;
CREATE TABLE IF NOT EXISTS `servicos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(40) NOT NULL,
  `descricao` varchar(150) NOT NULL,
  PRIMARY KEY (`id`)
);

-- --------------------------------------------------------

--
-- Estrutura da tabela `servicos_solicitados`
--

DROP TABLE IF EXISTS `servicos_solicitados`;
CREATE TABLE IF NOT EXISTS `servicos_solicitados` (
  `solicitacao` int NOT NULL,
  `id` int NOT NULL
);

-- --------------------------------------------------------

--
-- Estrutura da tabela `solicitacao`
--

DROP TABLE IF EXISTS `solicitacao`;
CREATE TABLE IF NOT EXISTS `solicitacao` (
  `id` int NOT NULL AUTO_INCREMENT,
  `inicio` varchar(10) DEFAULT NULL,
  `fim` varchar(10) DEFAULT NULL,
  `encarregado` int DEFAULT NULL,
  `solicitante` varchar(40) DEFAULT NULL,
  `embarcacao` varchar(40),
  `porto` varchar(40) DEFAULT NULL,
  `obs` varchar(200),
  PRIMARY KEY (`id`)
);

-- --------------------------------------------------------

--
-- Estrutura da tabela `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
CREATE TABLE IF NOT EXISTS `usuarios` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(40) DEFAULT NULL,
  `sobrenome` varchar(40) DEFAULT NULL,
  `email` varchar(40),
  `usuario` varchar(40) DEFAULT NULL,
  `senha` varchar(40) DEFAULT NULL,
  `cargo` varchar(5) NOT NULL,
  PRIMARY KEY (`id`)
);

--
-- Extraindo dados da tabela `usuarios`
--

INSERT INTO `usuarios` (`id`, `nome`, `sobrenome`, `email`, `usuario`, `senha`, `cargo`) VALUES
(1, 'Admin', 'Admin', '', 'Admin', '0000', 'Adm');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;