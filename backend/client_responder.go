package main


import (
    "net/http"
    "log"
    "io/ioutil"
    "encoding/json"
    "fmt"
)

type json_request struct{
    Type string
    ID string
    Pref []float32
    Push bool
}

func requestParser(w http.ResponseWriter, r *http.Request){
    var f json_request
    body, err := ioutil.ReadAll(r.Body)
    if err != nil {
        panic(err)
    }

    err = json.Unmarshal(body,&f)
    if err != nil {
        panic(err)
    }

    switch f.Type{
    case "send_price":
        sendCurrentPrice()
    }
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
