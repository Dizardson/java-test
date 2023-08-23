import java.io.*;

public class Main {
    public static void main(String[] args) {
        if(args.length == 0){
            System.out.println("Arguments were not given");
        } else {
            choiseFunction(args);
        }
        System.out.println("The program has been successfully completed");
    }

    public static void choiseFunction(String[] args) {
        int amountArgs = 0;
        int function = 0;
        switch (args[0]) {
            case ("-a") -> {
                amountArgs = 2;
                switch (args[1]) {
                    case ("-s") -> function = 1;
                    case ("-i") -> function = 2;
                    default -> System.out.println("Invalid arguments were specified at startup");
                }
            }
            case ("-d") -> {
                amountArgs = 2;
                switch (args[1]) {
                    case ("-s") -> function = 3;
                    case ("-i") -> function = 4;
                    default -> System.out.println("Invalid arguments were specified at startup");
                }
            }
            case ("-s") -> {
                amountArgs = 1;
                function = 1;
            }
            case ("-i") -> {
                amountArgs = 1;
                function = 2;
            }
            default -> System.out.println("Invalid arguments were specified at startup");
        }
        if (function != 0) {
            workWithFunction(args,amountArgs,function);
        }
    }

    private static void workWithFunction(String[] args, int amountArgs, int function) {
        int amountFiles = args.length - amountArgs - 1;
        if (amountFiles == -1) {
            System.out.println("No output file was specified");
        } else if (amountFiles == 0) {
            System.out.println("Input files not specified");
        } else {
            String[][] fileData = workWithFileData(args,amountArgs,amountFiles,function);
            String outName = args[amountArgs];
            String[] out = new String[0];
            int[][] fileDataInt;
            switch (function) {
                case (1):
                    out = sortString(fileData);
                    break;
                case (2) :
                    fileDataInt = toInt(fileData);
                    out = sortInt(fileDataInt);
                    break;
                case (3) : {
                    out = sortString(fileData);
                    reversOut(out);
                    break;
                }
                case (4) :{
                    fileDataInt = toInt(fileData);
                    out = sortInt(fileDataInt);
                    reversOut(out);
                    break;
                }
            }
            saveDataInFile(out, outName);
        }
    }

    public static String[][] workWithFileData(String[] args, int amountArgs,int amountFiles,int function) {
        String[] fileName = new String[amountFiles];
        for (int i = 0; i < amountFiles; i++) {
            fileName[i] = args[i + amountArgs + 1 ];
        }
        String[][] fileData = new String[amountFiles][];
        for (int i = 0; i < amountFiles; i++) {
            String[] s = takeDataFromFile(fileName[i]);
            if (function == 1 || function == 3) {
                if (!isSortingString(s)) {
                    //System.out.println("Файл не был изначально отсортирован " + fileName[i]);
                    s = sortFileString(s);
                }
            } else if (function == 2 || function == 4) {
                if (!isSortingInt(s)) {
                    //System.out.println("Файл не был изначально отсортирован " + fileName[i]);
                    s = sortFileInt(s);
                }
            }
            fileData[i] = s;
        }

        boolean[] notEmptyFile = new boolean[fileData.length];
        int countEmptyFiles = 0;
        for (int i = 0; i < fileData.length; i++) {
            notEmptyFile[i] = fileData[i].length != 1 || !fileData[i][0].equals(" ");
            if (!notEmptyFile[i]) {
                countEmptyFiles++;
            }
        }
        int countNotEmptyFiles = fileData.length - countEmptyFiles;
        if (countEmptyFiles != 0) {
            String[][] notEmptyFileData = new String[countNotEmptyFiles][];
            int j = 0;
            for (int i = 0; i < fileData.length; i++) {
                if (notEmptyFile[i]) {
                    notEmptyFileData[j] = fileData[i];
                    j++;
                }
            }
            fileData = notEmptyFileData;
        }
        return fileData;
    }

    private static String[] sortFileInt(String[] s) {
        String[][] dataForSort = new String[s.length][];
        for (int i = 0; i < s.length; i ++){
            dataForSort[i] = new String[1];
            dataForSort[i][0] = s[i];
        }
        int[][] dataAfterToInt = toInt(dataForSort);
        return sortInt(dataAfterToInt);
    }
    private static String[] sortFileString(String[] s) {
        String[][] dataForSort = new String[s.length][];
        for (int i = 0; i < s.length; i ++){
            dataForSort[i] = new String[1];
            dataForSort[i][0] = s[i];
        }
        return sortString(dataForSort);
    }

    private static boolean isSortingString(String[] s) {
        boolean isSort = true;
        for(int i = 0; i < s.length-1; i++){
            if (0 < s[i].compareTo(s[i + 1])) {
                isSort = false;
                break;
            }
        }
        return isSort;
    }
    private static boolean isSortingInt(String[] s) {
        boolean isSort = true;
        String[][] dataForSort = new String[s.length][];
        for (int i = 0; i < s.length; i ++){
            dataForSort[i] = new String[1];
            dataForSort[i][0] = s[i];
        }
        int[][] dataAfterToInt = toInt(dataForSort);
        int[] dataAfterToInt1 = new int[dataAfterToInt.length];
        int a = 0;
        for(int i = 0; i < dataAfterToInt.length; i++){
            if(dataAfterToInt[i].length != 0){
                dataAfterToInt1[a] = dataAfterToInt[i][0];
                a++;
            }
        }
        int[] dataAfterToInt2 = new int[dataAfterToInt1.length];
        for(int i = 0; i < dataAfterToInt2.length; i++){
            dataAfterToInt2[i] = dataAfterToInt1[i];
        }
        for(int i = 0; i < dataAfterToInt2.length-1; i++){
            try {
                if(dataAfterToInt2[i] < dataAfterToInt2[i+1]){
                    isSort = false;
                }
            } catch (ArrayIndexOutOfBoundsException ignored){}
        }
        return isSort;
    }

    private static String[] sortString(String[][] fileData) {
        int countData = 0;
        for (int i = 0; i < fileData.length; i++) {
            for (int j = 0; j < fileData[i].length; j++) {
                countData++;
            }
        }
        String[] out = new String[countData];
        int[] countDataInFile = new int[fileData.length];
        for(int i = 0; i < fileData.length; i ++){
            countDataInFile[i] = fileData[i].length;
        }
        int[] indexForSorting = new int[fileData.length];
        for(int i = 0; i < countData; i ++){
            int a = 0;
            for(int j = 1; j < fileData.length; j ++){
                if(indexForSorting[a]<countDataInFile[a]) {
                    if(indexForSorting[j]<countDataInFile[j]) {
                        if (0 <= fileData[a][indexForSorting[a]].compareTo(fileData[j][indexForSorting[j]])) {
                            a = j;
                        }
                    }
                }else{
                    a++;
                }
            }
            out[i] = String.valueOf(fileData[a][indexForSorting[a]]);
            indexForSorting[a]++;
        }
        return out;
    }

    private static int[][] toInt(String[][] fileData) {
        int[][] intData = new int[fileData.length][];
        boolean[][] brokenData = new boolean[fileData.length][];
        int[] countBrokenInLines = new int[fileData.length];
        int countBrokenData = 0;
        for(int i = 0; i < fileData.length; i++){
            brokenData[i] = new boolean[fileData[i].length];
            for(int j = 0; j < fileData[i].length; j ++){
                try{
                    int testNotExeption = Integer.parseInt(fileData[i][j]);
                    brokenData[i][j] = false;
                }catch (NumberFormatException e){
                    brokenData[i][j] = true;
                    countBrokenInLines[i]++;
                    countBrokenData++;
                }
            }
        }
        if(countBrokenData != 0){
            //System.out.println("В файлах имеются символы. Для продолжения сортировки чисел они были удалены");
            for(int i = 0; i < fileData.length; i++){
                if(countBrokenInLines[i]!= 0){
                    String[] notBrokenLine = new String[fileData[i].length-countBrokenInLines[i]];
                    int ind4NBL = 0;
                    for (int j = 0; j < fileData[i].length; j++){
                        if(!brokenData[i][j]){
                            notBrokenLine[ind4NBL] = fileData[i][j];
                            ind4NBL++;
                        }
                    }
                    fileData[i] = notBrokenLine;
                }
            }
        }
        for(int i = 0; i < fileData.length; i++){
            intData[i] = new int[fileData[i].length];
            for(int j = 0; j < fileData[i].length; j ++){
                intData[i][j] = Integer.parseInt(fileData[i][j]);
            }
        }
        return intData;
    }

    private static void reversOut(String[] out) {
        String[] outR = new String[out.length];
        System.arraycopy(out,0,outR,0,out.length);
        int j = out.length-1;
        for(int i =0 ; i < out.length;i++){
            out[i] = outR[j];
            j--;
        }
    }

    private static void saveDataInFile(String[] out,String outName)  {
        File f = new File(outName);
        try {
            if(f.createNewFile()){
                System.out.println("A file has been created with the name " + outName);
            }
            FileWriter fr = new FileWriter(f, false);
            for(int i = 0; i < out.length; i++){
                if(i == out.length-1){
                    fr.write( out[i]);
                } else {
                    fr.write( out[i]+"\n");
                }
            }
            fr.flush();
            System.out.println("Sorting completed successfully. The data is saved in a file: " + outName);
        } catch (IOException e) {
            System.out.println("The output file must be in the format filename.txt \nData not saved");
        }
    }

    private static String[] sortInt(int[][] intData) {
        int countData = 0;
        for (int[] intDatum : intData) {
            for (int j = 0; j < intDatum.length; j++) {
                countData++;
            }
        }
        String[] out = new String[countData];
        int[] countDataInFile = new int[intData.length];
        for(int i = 0; i < intData.length; i ++){
            countDataInFile[i] = intData[i].length;
        }
        int[] indexForSorting = new int[intData.length];
        for(int i = 0; i < countData; i ++){
            int a = 0;
            for(int j = 1; j < intData.length; j ++){
                if(indexForSorting[a]<countDataInFile[a]) {
                    if(indexForSorting[j]<countDataInFile[j] &&
                            intData[a][indexForSorting[a]] > intData[j][indexForSorting[j]]) {
                            a = j;
                    }
                }else{
                    a++;
                }
            }
            out[i] = String.valueOf(intData[a][indexForSorting[a]]);
            indexForSorting[a]++;
        }
        return out;
    }

    private static String[] takeDataFromFile(String fileName) {
        int countLine = 0;
        String[] data = new String[]{" "};
        int[] failData = new int[]{-2};
        try {
            String s;
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            //System.out.println("Название файла: " + fileName);
            int countFailLine = 0;
            while((s = reader.readLine()) != null) {
                if (s.equals(" ") || s.isEmpty()) {
                    countFailLine++;
                }
                countLine++;
            }
            if(countLine!= 0) {
                int countNotFailLine = countLine;
                if (countFailLine > 0) {
                    failData = new int[countNotFailLine];
                    //System.out.println("В данном файле были обнаружены строки с пробелами и они были удалены");
                    countFailLine = 0;
                    reader.close();
                    reader = new BufferedReader(new FileReader(fileName));
                    countNotFailLine = 0;
                    for (int i = 0; i < countLine; i++) {
                        s = reader.readLine();
                        s = s.replaceAll("\\s*", "");
                        if (s.equals(" ") || s.isEmpty()) {
                            failData[countFailLine] = i;
                            countFailLine++;
                        } else {
                            countNotFailLine++;
                        }
                    }
                }
                countFailLine = 0;
                reader.close();
                reader = new BufferedReader(new FileReader(fileName));
                data = new String[countNotFailLine];
                int j = 0;
                for (int i = 0; i < countLine; i++) {
                    if (i == failData[countFailLine]) {
                        if (countFailLine < failData.length) {
                            countFailLine++;
                            reader.readLine();
                        }
                    } else {
                        data[j] = reader.readLine();
                        //System.out.print(data[j] + " ");
                        j++;
                    }
                }
            } else{
                System.out.println("File " + fileName + " is empty");
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File with name " + fileName + " not found");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return data;
    }
}
