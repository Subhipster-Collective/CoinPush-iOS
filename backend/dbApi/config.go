package dbApi
import "fmt"

func GetDatabaseInfo() string {
    return(fmt.Sprintf("%s:%s@/%s","bmassoumi","E101raiders","coinPushdb"))
}
