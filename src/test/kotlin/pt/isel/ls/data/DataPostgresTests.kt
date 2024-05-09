package pt.isel.ls.data

import pt.isel.ls.CONN_NAME
import pt.isel.ls.data.postgres.DataPostgres

abstract class DataPostgresTests: AbstractDataTests(DataPostgres(System.getenv(CONN_NAME)))