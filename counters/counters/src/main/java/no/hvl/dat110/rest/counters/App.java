package no.hvl.dat110.rest.counters;

import static spark.Spark.after;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.put;
import static spark.Spark.delete;

import java.util.List;

import static spark.Spark.post;

import com.google.gson.Gson;

/**
 * Hello world!
 *
 */
public class App {
	
	static Counters counters = null;
	static TodoDAO todoDAO = null;
	
	public static void main(String[] args) {

		if (args.length > 0) {
			port(Integer.parseInt(args[0]));
		} else {
			port(8080);
		}

		todoDAO = new TodoDAO();
		
		after((req, res) -> {
  		  res.type("application/json");
  		});
		
		get("/hello", (req, res) -> "Hello World!");
		
		get("/todos", (req, res) -> {
			Gson gson = new Gson();
			List<Todo> todoList = todoDAO.getAll();
			return gson.toJson(todoList);
		});
		
		post("/todo", (req, res) -> {
			Gson gson = new Gson();
			Todo todo = gson.fromJson(req.body(), Todo.class);
			todoDAO.save(todo);
			return todo.toJson();
		});
		
		put("/todo", (req, res) -> {
			Gson gson = new Gson();
			Todo todo = gson.fromJson(req.body(), Todo.class);
			todoDAO.update(todo);
			return todo.toJson();
		});
		
		delete("/todo", (req, res) -> {
			Gson gson = new Gson();
			Todo todo = gson.fromJson(req.body(), Todo.class);
			todoDAO.delete(todo);
			return todo.toJson();
		});
		
        get("/counters", (req, res) -> counters.toJson());
               
        put("/counters", (req,res) -> {
        
        	Gson gson = new Gson();
        	
        	counters = gson.fromJson(req.body(), Counters.class);
        
            return counters.toJson();
        	
        });
    }
    
}
