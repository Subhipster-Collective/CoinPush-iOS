package dbApi

import (
    "fmt"
    "database/sql"
    _ "github.com/go-sql-driver/mysql"
)

type DataObj *sql.DB

func (DataObj) UserExists(ID string){

}

func OpenConnection() DataObj {
    options := GetDatabaseInfo()
    db, err := sql.Open("mysql", options)
    err := db.Ping()
    if err != nil {
        panic(err)
    }
    return(DataObj(db))
}
