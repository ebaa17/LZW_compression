
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class LZW {
    public static ArrayList<Integer> LZWCompression(String text) {
        ArrayList<String> dictionary = new ArrayList<>();
        for (int i = 0; i < 128; i++) {
            dictionary.add(Character.toString(i));
        }
        ArrayList<Integer> compressed = new ArrayList<>();
        String current = ""; //longest match
        for (char c : text.toCharArray()) { //hakhod el char el 3aleeh el dor fel input ahoto f c
            String next = current + c;
            //next da el huwa bakhod el current wel c (atwal haga +el harf el ana feeh dlwa2te).
            // (wana f awel harf el current fady wel c heya el awel harf).
            //ba compress el fel current mesh el fel c//
            if (dictionary.contains(next)) {
                current = next;
                //law el next mawgod hazawed 3aleeh el current w ashuf el warah lehad ma ala2y atwal haga mawgoda
                //el current ana 3ayzah yeb2a longest match mawgod fel dictionary.
            } else {
                compressed.add(dictionary.indexOf(current));
                dictionary.add(next);
                current = "" + c;
                // lama mala2eesh el next mawgood hahoto fel dictionary w hakhod code el current mel dictionary
                //(el current dayman huwa el next match ela harf)
            }
        }
        if (!current.isEmpty()) { //law fadel haga fel akher falatet mel compression bakhodha
            //hatehsal law akher haga fel array mawgoda already fel dict, fa haye3mel current = next w yelhrog mel loop
            //fa hahoto ana hena ba2a.
            compressed.add(dictionary.indexOf(current));
        }
        return compressed;
    }


    public static String LZWDecompression(List<Integer> compressed){
        StringBuilder decompressed= new StringBuilder();
        Map<Integer , String> dictionary=new HashMap<>();
        for (int i = 0; i < 128; i++) {
            dictionary.put(i, Character.toString(i));
        }

        int nextDictionaryCode=128;


        String dictionaryString = dictionary.get(compressed.get(0));
        decompressed.append(dictionaryString);

        for(int i=1 ; i<compressed.size(); i++){
            Integer compressedCode= compressed.get(i);

            if(dictionary.containsKey(compressedCode)){
                dictionaryString = dictionary.get(compressed.get(i));
                decompressed.append(dictionaryString);//put the string directly

                //now make the new (code , string)
                //elly 2bly+ 2wel 7rf mn elly b3dy
                String prevString = dictionary.get(compressed.get(i-1));//2lly 2bly
                String nextDictionaryString=prevString+dictionaryString.charAt(0);

                dictionary.put(nextDictionaryCode,nextDictionaryString);
                nextDictionaryCode++;
            }

            // lw el code m4 mwgood asln
            else{
                //elly 2bly+ 2wel 7rf bta3oo
                String prevString = dictionary.get(compressed.get(i-1));
                String nowDictionaryString=prevString+prevString.charAt(0);

                dictionary.put(nextDictionaryCode,nowDictionaryString);
                nextDictionaryCode++;

                decompressed.append(nowDictionaryString);
            }
        }
        return decompressed.toString();

    }

    public static void main(String[] args) throws IOException {
        File file = new File("Input.txt");
        String input = Files.readString(file.toPath()).trim();

        if (file.exists()){
            // Compress the input
            List<Integer> compressed = LZWCompression(input);
            String decompressed = LZWDecompression(compressed);
//            System.out.println("Compressed output: " + compressed);
//            System.out.println("Decompressed output: " + decompressed);

            Path outputFile = Paths.get("Output.txt");
            if (!Files.exists(outputFile)) {
                Files.createFile(outputFile);
            }
            StringBuilder outputContent = new StringBuilder();
            outputContent.append("Compressed: ").append(compressed.toString().replaceAll("[\\[\\],]", "")).append("\n");
            outputContent.append("Decompressed: ").append(decompressed);

            Files.writeString(outputFile, outputContent.toString());

            // verify en huwa huwa
            System.out.println("Check: " + input.equals(decompressed));
        }else{
            System.out.println("Error, File doesn't exist!");
        }
    }


//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        System.out.print("Enter a string to compress: ");
//        String input = scanner.nextLine();
//        scanner.close();
//
//        List<Integer> compressed = LZWCompression(input);
//        System.out.println("Compressed output: " + compressed);
//
//        String decompressed = LZWDecompression(compressed);
//        System.out.println("Decompressed output: "+ decompressed);
//
//        System.out.println("Checkkk: " +input.equals(decompressed));
//    }

}
