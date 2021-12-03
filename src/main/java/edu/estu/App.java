package edu.estu;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import sun.nio.cs.UTF_8;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Collator;
import java.util.*;

public class App {
    public static void main( String[] args ) {
        ArrayList<String> mainList = new ArrayList<>();
        Myoptions myoptions = new Myoptions();
        CmdLineParser parser = new CmdLineParser(myoptions);

        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("java -jar myprogram.jar [options...] arguments...");
            parser.printUsage(System.err);
            return;
        }

        int numberOfFile = myoptions.fileName.length;
        for (int a = 0 ; a<numberOfFile ; a++){
            Path path = Paths.get(myoptions.fileName[a]);
            if (Files.notExists(path)) {
                System.out.println("the file"+myoptions.fileName[a]+" does not exist!");
                return;
            }

            List<String> lines;
            try {
                lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            } catch (IOException ioe) {
                ioe.printStackTrace();
                return;
            }


            for (int i = 0 ; i<lines.size();i++){
                StringTokenizer st = new StringTokenizer(lines.get(i));
                while (st.hasMoreTokens()) {
                    String token = st.nextToken().toLowerCase(Locale.ENGLISH);
                    for (int j = 0 ; j<token.length() ; j++){
                        if (!Character.isLetterOrDigit(token.charAt(j))){
                            String cX = String.valueOf(token.charAt(j));
                            token = token.replace(cX,"");
                            j--;
                        }
                    }
                    if (!(token.length()==0))
                        mainList.add(token);

                }
            }
        }


        if (myoptions.unique){
            mainList = new ArrayList<>(new HashSet<>(mainList));
        }
        if (myoptions.reverse){
            Collections.reverse(mainList);
        }
        if (myoptions.task.equals("NumOfTokens")){
            System.out.println(mainList.size());
        }
        if (myoptions.task.equals("TermLengthStats")){
            int max = mainList.get(0).length() ;
            int min = mainList.get(0).length() ;
            double average = 0 ;

            for (String s : mainList) {
                average+=s.length();
                if (s.length() > max)
                    max = s.length();
                if (s.length() < min)
                    min = s.length();
            }

            System.out.println("Max Token Length in Character: "+max);
            System.out.println("Min Token Length: "+min);
            System.out.println("Average Token Length: "+average/((double) mainList.size()));
        }




        if (myoptions.task.equals("TermsStartWith")){
            int topN = myoptions.topN;

            mainList = new ArrayList<>(new HashSet<>(mainList));
            Collator trCollator = Collator.getInstance(Locale.forLanguageTag("TR-tr"));
            trCollator.setStrength(Collator.PRIMARY); // ignores casing
            Collections.sort(mainList, trCollator);

            if (myoptions.reverse)
                Collections.reverse(mainList);

            for (String s : mainList) {
                if (s.startsWith(myoptions.start) && topN!=0){
                    System.out.println(s);
                    topN--;
                }
            }
        }


        if (myoptions.task.equals("FrequentTerms")){
            ArrayList<MyGenericList> dataBase = new ArrayList<>();
            ArrayList<String> uList = new ArrayList<>(new HashSet<>(mainList));
            Collator trCollator = Collator.getInstance(Locale.forLanguageTag("TR-tr"));
            trCollator.setStrength(Collator.PRIMARY); // ignores casing
            Collections.sort(mainList, trCollator);
            Collections.sort(uList,trCollator);

            if (!myoptions.reverse){
                Collections.reverse(uList);
            }


            for (String uToken : uList){
                int counter = 0 ;
                for (String token : mainList){
                    if (uToken.equals(token))
                        counter++;
                }

                MyGenericList<String,Integer> newData = new MyGenericList<>(uToken,counter);
                if (dataBase.size()==0){
                    dataBase.add(newData);
                }
                else {
                    boolean swapStat = false;
                    for (int i = 0 ; i< dataBase.size() ; i++){
                        if (((int)(newData.getSecond()))>=((int)(dataBase.get(i).getSecond()))){
                            dataBase.add(i,newData);
                            i= dataBase.size();
                            swapStat = true;
                        }
                    }
                    if (!swapStat)
                        dataBase.add(newData);
                }



            }

            if (myoptions.reverse){
                Collections.reverse(dataBase);
            }

            for (int i = 0 ; i< myoptions.topN;i++)
                dataBase.get(i).print();

        }

    }

}
