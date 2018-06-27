import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class MarkDownToHTML {

    private static void check_list(String Prev_Line, String Line) throws IOException {
        if(!(Line.length() > 0)){
            if(Prev_Line.length() > 0){
                if(Prev_Line.substring(0, 2).equals("* ")){
                    FileWriter fw  = new FileWriter("html.txt",true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write("</ul>");
                    bw.newLine();
                    bw.close();
                }
            }
        }
        
        else if(Prev_Line.length() > 0){
            if(Line.length() > 0){
                if(Line.substring(0, 2).equals("* ") && Prev_Line.substring(0, 2).equals("* ")){
                    FileWriter fw  = new FileWriter("html.txt",true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write("<li>");
                    bw.write(Line.substring(2, Line.length()));
                    bw.write("</li>");
                    bw.newLine();
                    bw.close();
                }
                else if(Prev_Line.substring(0, 2).equals("* ") && Line.split(" ")[0].contains("*")){
                    FileWriter fw  = new FileWriter("html.txt",true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write("<ul>");
                    bw.newLine();
                    bw.write("<li>");
                    bw.write(Line.split(" ")[1]);
                    bw.write("</li>");
                    bw.newLine();
                    bw.close();
                }
                else if(Prev_Line.split(" ")[0].contains("*") && Line.substring(0, 2).equals("* ")){
                    FileWriter fw  = new FileWriter("html.txt",true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write("</ul>");
                    bw.newLine();
                    bw.write("<li>");
                    bw.write(Line.substring(2, Line.length()));
                    bw.write("</li>");
                    bw.newLine();
                    bw.close();
                }
                
                else if(Prev_Line.split(" ")[0].contains("*") && Line.split(" ")[0].contains("*")){
                    if(Line.split(" ").length >= 2){
                        FileWriter fw  = new FileWriter("html.txt",true);
                        BufferedWriter bw = new BufferedWriter(fw);
                        bw.write("<li>");
                        bw.write(Line.split(" ")[1]);
                        bw.write("</li>");
                        bw.newLine();
                        bw.close();
                    }
                }
            }
        }
        
        else{
            if(Line.length() > 0 && Line.substring(0, 2).equals("* ")){
                FileWriter fw  = new FileWriter("html.txt",true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write("<ul>");
                bw.newLine();
                bw.write("<li>");
                bw.write(Line.substring(2, Line.length()));
                bw.write("</li>");
                bw.newLine();
                bw.close();
            }
        }
        
    }
    
    public static void check_anchors(String Line) throws IOException{
        if(Line.length() > 0){
            int index = Line.indexOf(']');
            if(Line.substring(0, 1).equals("[")){
                String link_name = Line.substring(1, index);
                String link_location = Line.substring( index+2, Line.length()-1);
                FileWriter fw  = new FileWriter("html.txt",true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write("<a href=\"");
                bw.write(link_location);
                bw.write("\">");
                bw.write(link_name);
                bw.write("</a>");
                bw.newLine();
                bw.close();
            }
        }
    }
    
    public static void check_images(String Line) throws IOException{
        if(Line.length() > 0){
            int index = Line.indexOf(']');
            if(Line.substring(0, 1).equals("!")){
                String link_name = Line.substring(2, index);
                String link_location = Line.substring( index+2, Line.length()-1);
                FileWriter fw  = new FileWriter("html.txt",true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write("<img src=\"");
                bw.write(link_location);
                bw.write("\" alt=\"");
                bw.write(link_name);
                bw.write("\"/>");
                bw.newLine();
                bw.close();
            }
        }
    }
    
    
     public static void check_quote(String Line) throws IOException{
         if(Line.length() > 0){
            String[] words = Line.split(" ");
            if( words[0].equals(">")){
                FileWriter fw  = new FileWriter("html.txt",true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write("<blockquote>");
                bw.close();
                int i = 1;
                String check;
                while(i < words.length){
                    check = words[i];
                    if(check.substring(0, 1).equals("*") || check.substring(0, 1).equals("[")){
                        if(check.substring(0, 1).equals("*")){
                            check_text_manipulations(check);
                        }
                        else{
                            check_anchors(check);
                        }
                    }
                    else{
                        FileWriter fw1  = new FileWriter("html.txt",true);
                        BufferedWriter bw1 = new BufferedWriter(fw1);
                        bw1.write(check);
                        bw1.write(" ");
                        bw1.close();
                    }
                    i++;
                }
                FileWriter fw2  = new FileWriter("html.txt",true);
                BufferedWriter bw2 = new BufferedWriter(fw2);
                bw2.write("</blockquote>");
                bw2.newLine();
                bw2.close();
            }
         }
    }
   
    
    public static void check_headings(String Line) throws IOException{
        
        String[] headers = Line.split(" ");
        if(headers[0].length() == 1){
            if(headers[0].equals("#")){
                FileWriter fw  = new FileWriter("html.txt",true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write("<h1>");
                int len = Line.length();
                String Lin = Line.substring(2, len);
                bw.write(Lin);
                bw.write("</h1>");
                bw.newLine();
                bw.close();
            }
        }
        
        
        else if(headers[0].length() == 2){
            if(headers[0].equals("##")){
                FileWriter fw  = new FileWriter("html.txt",true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write("<h2>");
                int len = Line.length();
                String Lin = Line.substring(3, len);
                bw.write(Lin);
                bw.write("</h2>");
                bw.newLine();
                bw.close();
            }
        }
        
        else if(headers[0].length() == 3){
            if(headers[0].equals("###")){
                FileWriter fw  = new FileWriter("html.txt",true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write("<h3>");
                int len = Line.length();
                String Lin = Line.substring(4, len);
                bw.write(Lin);
                bw.write("</h3>");
                bw.newLine();
                bw.close();
            }
        }
        
        else if(headers[0].length() == 4){
            if("####".equals(headers[0])){
                FileWriter fw  = new FileWriter("html.txt",true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write("<h4>");
                int len = Line.length();
                String Lin = Line.substring(5, len);
                bw.write(Lin);
                bw.write("</h4>");
                bw.newLine();
                bw.close();
            }
        }
        
        else if(headers[0].length() == 5){
            if("#####".equals(headers[0])){
                FileWriter fw  = new FileWriter("html.txt",true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write("<h5>");
                int len = Line.length();
                String Lin = Line.substring(6, len);
                bw.write(Lin);
                bw.write("</h5>");
                bw.newLine();
                bw.close();
            }
        }
        
        else if(headers[0].length() == 6){
            if("######".equals(headers[0])){
                FileWriter fw  = new FileWriter("html.txt",true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write("<h6>");
                int len = Line.length();
                String Lin = Line.substring(7, len);
                bw.write(Lin);
                bw.write("</h6>");
                bw.newLine();
                bw.close();
            }
        }
        
    }
    
    
    public static void check_text_manipulations(String Line) throws IOException{
        FileWriter fw  = new FileWriter("html.txt",true);
        BufferedWriter bw = new BufferedWriter(fw);
        int Len = Line.length();
        
        if(Len > 0){
            if(Line.substring(0, 1).equals("*") && !Line.substring(1, 2).equals("*") && !Line.substring(1, 2).equals(" ")){
                String Sentence = Line.substring(1, Len-1);
                bw.write("<i>");
                bw.write(Sentence);
                bw.write("</i>");
                bw.newLine();
                bw.close();
            }
            else if(Line.substring(0, 2).equals("**") && !Line.substring(2, 3).equals("*") && !Line.substring(1, 2).equals(" ")){
                String Sentence = Line.substring(2, Len-2);
                bw.write("<b>");
                bw.write(Sentence);
                bw.write("</b>");
                bw.newLine();
                bw.close();
            }
            else if(Line.substring(0, 3).equals("***") && !Line.substring(1, 2).equals(" ")){
                String Sentence = Line.substring(3, Len-3);
                bw.write("<b>");
                bw.write("<i>");
                bw.write(Sentence);
                bw.write("</i>");
                bw.write("</b>");
                bw.newLine();
                bw.close();
            }

            bw.close();
        }
        bw.close();
        
    }
    
    public static void check_line(String Line) throws IOException{
        String[] words = Line.split(" ");
        FileWriter fw  = new FileWriter("html.txt",true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("<p>");
        bw.close();
        int i = 0;
        String check;
        while(i < words.length){
            check = words[i];
            if(check.substring(0, 1).equals("*") || check.substring(0, 1).equals("[")){
                if(check.substring(0, 1).equals("*")){
                    check_text_manipulations(check);
                }
                else{
                    check_anchors(check);
                }
            }
            else{
                FileWriter fw1  = new FileWriter("html.txt",true);
                BufferedWriter bw1 = new BufferedWriter(fw1);
                bw1.write(check);
                bw1.write(" ");
                bw1.close();
            }
            i++;
        }
        FileWriter fw2  = new FileWriter("html.txt",true);
        BufferedWriter bw2 = new BufferedWriter(fw2);
        bw2.write("</p>");
        bw2.newLine();
        bw2.close();
        
    }
    
     
    public static void main(String[] args) throws FileNotFoundException, IOException {
        FileReader fp = new FileReader("read_md.txt");
        BufferedReader br = new BufferedReader(fp);
        
        File fq = new File("html.txt");
        fq.createNewFile();
        FileWriter fw  = new FileWriter(fq,false);
        BufferedWriter bw = new BufferedWriter(fw);
        
        bw.write("<html>");
        bw.newLine();
        bw.write("<head>");
        bw.newLine();
        bw.write("<title>MarkDown to HTML File</title>");
        bw.newLine();
        bw.write("</head>");
        bw.newLine();
        bw.write("<body>");
        bw.newLine();
        bw.close();
        
        String Line,Prev_Line="";
        Line = br.readLine();
        while((Line)!= null){
            check_headings(Line);
            check_text_manipulations(Line);
            check_anchors(Line);
            check_images(Line);
            check_quote(Line);
            check_list(Prev_Line,Line);
            
            if(Line.length() > 0 && !Line.substring(0, 1).equals("*") && !Line.substring(0, 1).equals("[") && !Line.substring(0, 1).equals("!") && !Line.substring(0, 1).equals("#") && !Line.substring(0, 1).equals(">") && !Line.split(" ")[0].contains("*")){
                check_line(Line);
            }
            Prev_Line = Line;
            Line = br.readLine();
        }
        
        
        FileWriter fw1  = new FileWriter("html.txt",true);
        BufferedWriter bw1 = new BufferedWriter(fw1);
        bw1.write("</body>");
        bw1.newLine();
        bw1.write("</html>");
        bw1.close();
        
    }
    
}
