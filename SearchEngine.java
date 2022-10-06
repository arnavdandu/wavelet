import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    List<String> searches = new ArrayList<>();
    

    private String output(String substring) {
        String output = "";
        if(substring.equals("")) {
            for(String search : searches) {
                output += search + " ";
            }
        } else {
            for(String search : searches) {
                if(search.contains(substring)) {
                    output += search + " ";
                }
            }
        }
        return output;
    }

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return "All previous searches: " + output("");
        } else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    searches.add(parameters[1]);
                    return "Added search.";
                }
            } else if (url.getPath().contains("/search")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    return output(parameters[1]);
                }
            }
            return "404 Not Found!";
        }
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}