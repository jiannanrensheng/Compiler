package Complier;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
	public static void main(String args[]) {
		try {
			// 词法分析结果写入WordAnalyse.txt中
			WordAnalyse c1 = new WordAnalyse("src/test.c");
			String s1 = null;
			File f1 = new File("src/WordAnalyseResult.txt");
			BufferedWriter br1 = new BufferedWriter(new FileWriter(f1));
			ArrayList<Word> wordAnalyseResult = c1.WordAnalyse();
			System.out.println("词法分析成功");
			for (int i = 0; i < wordAnalyseResult.size(); i++) {
				s1 = " (" + wordAnalyseResult.get(i).getValue() + "," + wordAnalyseResult.get(i).getCode() + ")";
				br1.write(s1);
				br1.newLine();
			}
			br1.flush();

			// 语法分析
			GrammarAnalyse c2 = new GrammarAnalyse(wordAnalyseResult);
			String s2 = null;
			File f2 = new File("src/GrammarAnalyseResult.txt");
			BufferedWriter br2 = new BufferedWriter(new FileWriter(f2));
			c2.analyse();
			System.out.println("语法分析成功");
			ArrayList<String> grammarAnalyseResult = c2.getGrammarAnalyseResult();
			for (int i = 0; i < grammarAnalyseResult.size(); i++) {
				s2 = "\t" + grammarAnalyseResult.get(i);
				br2.write(s2);
				br2.newLine();
			}
			br2.flush();

			// 语义分析和中间代码生成
			SemanticAnalyse c3 = new SemanticAnalyse(wordAnalyseResult);
			String s3 = null;
			File f3 = new File("src/SemanticAnalyseResult.txt");
			BufferedWriter br3 = new BufferedWriter(new FileWriter(f3));
			c3.analyse();
			System.out.println("语义分析和中间代码生成成功");
			ArrayList<Quaternary> objList = c3.getSemanticAnalyseResult();

			for (Quaternary quaternary : objList) {
				s3 = "\t" + quaternary.toString();
				br3.write(s3);
				br3.newLine();
			}
			br3.flush();

			// 最终代码生成
			FinalCode c4 = new FinalCode(c3.getObList(), c3.getSemanticAnalyseResult());
			String s4 = null;
			File f4 = new File("src/FinalCodeResult.txt");
			BufferedWriter br4 = new BufferedWriter(new FileWriter(f4));
			c4.generateFinalCode();
			System.out.println("最终代码生成成功");
			for (String finalcode : c4.getFinalCodeList()) {
				s4 = finalcode;
				br4.write(s4);
				br4.newLine();
			}
			br4.flush();
		} catch (FileNotFoundException e) {
			System.out.println("未找到指定文件");
		} catch (IOException e) {
			System.out.println("读或写文件异常");
		}

		catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());

		}

	}
}
