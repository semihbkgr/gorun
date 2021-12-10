INSERT INTO snippets(id, version, display_order, title, brief, explanation, code)
VALUES (1, 1, -1, 'Hello World', 'brief', 'explanation', 'package main
import "fmt"
func main() {
	fmt.Println("hello world")
}');
INSERT INTO snippets(id, version, display_order, title, brief, explanation, code)
VALUES (2, 1, -1, 'Input Test', 'brief', 'explanation', 'package main
import "fmt"
func main() {
	fmt.Println("hello world")
	var name string
	fmt.Scanln(&name)
	fmt.Println(name)
}');
INSERT INTO snippets(id, version, display_order, title, brief, explanation, code)
VALUES (3, 1, -1, 'For Loop', 'brief', 'explanation', 'package main
import "fmt"
func main() {
	for i := 1;  i<=5; i++ {
		fmt.Printf("Welcome %d times\n",i)
	}
}');
INSERT INTO snippets(id, version, display_order, title, brief, explanation, code)
VALUES (4, 1, -1, 'Blocking For Loop', 'brief', 'explanation', 'package main
import (
	"fmt"
	"time"
)
func main() {
	for i := 1;  i<=5; i++ {
		fmt.Printf("Start Unix Time: %v\n", time.Now().Unix())
	time.Sleep(1 * time.Second)	}
}');
INSERT INTO snippets(id, version, display_order, title, brief, explanation, code)
VALUES (5, 1, -1, 'Sleep', 'brief', 'explanation', 'package main
import (
	"fmt"
	"time"
)
func main() {
	fmt.Printf("Start Unix Time: %v\n", time.Now().Unix())
	time.Sleep(2 * time.Second)
	fmt.Printf("Current Unix Time: %v\n", time.Now().Unix())
	time.Sleep(2 * time.Second)
	fmt.Printf("Current Unix Time: %v\n", time.Now().Unix())
	time.Sleep(2 * time.Second)
	fmt.Printf("Current Unix Time: %v\n", time.Now().Unix())
	time.Sleep(2 * time.Second)
	fmt.Printf("Current Unix Time: %v\n", time.Now().Unix())
	time.Sleep(2 * time.Second)
	fmt.Printf("End Unix Time: %v\n", time.Now().Unix())
}');
INSERT INTO snippets(id, version, display_order, title, brief, explanation, code)
VALUES (6, 1, -1, 'Name input', 'brief', 'explanation', 'package main
import "fmt"
func main() {
	fmt.Print("Name : ")
	var name string
	fmt.Scanln(&name)
	fmt.Printf("Your name %s", name)
}');