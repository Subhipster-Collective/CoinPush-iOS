package main


import (
    "fmt"
    "net/http"
    "log"
    "encoding/json"
    "io/ioutil"
)

type json_request struct {
    Test string
}

func requestParser(w http.ResponseWriter, r *http.Request){
    body, err := ioutil.ReadAll(r.Body)
    if err != nil {
        panic(err)
    }

    var data json_request
    json.Unmarshal(body, &data)
    fmt.Fprintf(w, data.Test)
    fmt.Fprintf(w, "\n")

}

//func sendCurrentPrice()
//func updateUserPrefrences()
//func sendUserPrefrences()
//func clearUserPrefrences()

func main() {
    http.HandleFunc("/handle_request", requestParser)
    err := http.ListenAndServe(":8082", nil)
    if err != nil {
        log.Fatal("ListenAndServe: ", err)
    }
}
