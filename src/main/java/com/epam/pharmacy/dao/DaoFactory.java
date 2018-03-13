package com.epam.pharmacy.dao;

import java.io.IOException;
import java.sql.Connection;

import by.evidences.mysql.MySqlBlogDao;
import by.evidences.mysql.MySqlContactdataDao;
import by.evidences.mysql.MySqlDepartmentsDao;
import by.evidences.mysql.MySqlDistrictsDao;
import by.evidences.mysql.MySqlFilesDao;
import by.evidences.mysql.MySqlLocalitiesDao;
import by.evidences.mysql.MySqlUsersDao;

/** Fabric of object to work with DataBase*/
public interface DaoFactory<Connection> {

	MySqlBlogDao getBlogDao() throws PersistException, IOException;

	MySqlContactdataDao getContactDataDao() throws PersistException, IOException;

	MySqlDepartmentsDao getDepartmentsDao() throws PersistException, IOException;

	MySqlDistrictsDao getDistrictsDao() throws PersistException, IOException;

	MySqlFilesDao getFilesDao() throws PersistException, IOException;

	MySqlLocalitiesDao getLocalitiesDao() throws PersistException, IOException;

	MySqlUsersDao getUsersDao() throws PersistException, IOException;
}
