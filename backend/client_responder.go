package main

import (
    "net/http"
    "log"
    "io/ioutil"
    "encoding/json"
    "fmt"
)

type ClientRequest struct{
    Type string
    Coin string
    ID string
    Pref []float32
    Push bool
}

func requestParser(w http.ResponseWriter, r *http.Request){
    var f ClientRequest
    body, err := ioutil.ReadAll(r.Body)
    if err != nil {
        panic(err)
    }

    err = json.Unmarshal(body,&f)
    log.Println(f)

    if err != nil {
        panic(err)
    }
    log.Println("after")

    switch f.Type{
    case "update":
        fmt.Fprintf(w,f.Coin)
        fmt.Fprintf(w, "got it")
    }
}


//func updateUserPrefrences()
//func clearUserPrefrences()

func main() {
    http.HandleFunc("/handle_request", requestParser)
    err := http.ListenAndServe(":8082", nil)
    if err != nil {
        log.Fatal("ListenAndServe: ", err)
    }
}
