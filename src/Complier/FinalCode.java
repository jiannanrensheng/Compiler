package Complier;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class FinalCode {
	// obList存放标识符和辅助变量,semanticAnalyseResult存放四元式列表
	private ArrayList<String> finalCodeList;
	private LinkedHashMap<String, Node> obList;
	private ArrayList<Quaternary> semanticAnalyseResult;
	private ArrayList<String> labelList;

	public FinalCode(LinkedHashMap<String, Node> obList, ArrayList<Quaternary> semanticAnalyseResult) {
		finalCodeList = new ArrayList<String>();
		this.obList = obList;
		this.semanticAnalyseResult = semanticAnalyseResult;
		labelList = new ArrayList<String>();
		for (int i = 0; i < semanticAnalyseResult.size(); i++) {
			String calculator = semanticAnalyseResult.get(i).getCaculator();
			String obj1 = null;
			if (calculator.equals("FJ") || calculator.equals("RJ")) {
				obj1 = semanticAnalyseResult.get(i).getOb1();
				labelList.add(obj1);
			}
		}
	}

	public void generateFinalCode() {
		/*
		 * .386p .model flat,stdcall option casemap:none
		 * 
		 * include windows.inc include kernel32.inc includelib kernel32.lib
		 */
		finalCodeList.add(".386");
		finalCodeList.add(".model flat,stdcall");
		finalCodeList.add("option casemap:none");
		finalCodeList.add("include windows.inc");
		finalCodeList.add("include masm32.inc");
		finalCodeList.add("include kernel32.inc");
		finalCodeList.add("includelib masm32.lib");
		finalCodeList.add("includelib kernel32.lib");
		finalCodeList.add(".data"); // 定义数据

		for (Entry<String, Node> entry : obList.entrySet()) {
			// 如果key值和Node的value值相同，说明式一个常量，则跳过即可
			char firstCharacter = entry.getKey().charAt(0);
			// 遇到数字常量和字符常量以及布尔常量跳过
			if (Character.isDigit(firstCharacter) || firstCharacter == '\'') {
				continue;
			} else if (entry.getKey().equals("true") || entry.getKey().equals("false")) {
				continue;
			}

			switch (entry.getValue().getType()) {
			case "int":
				finalCodeList.add("\t" + entry.getKey() + "\tDWORD\t" + 0);
				break;
			case "char":
				finalCodeList.add("\t" + entry.getKey() + "\tSBYTE\t" + 0);
				break;
			case "bool":
				finalCodeList.add("\t" + entry.getKey() + "\tBYTE\t" + 1);
				break;
			}
		}

		finalCodeList.add(".code");
		finalCodeList.add("main PROC"); // 主函数内容开始
		for (int i = 0; i < semanticAnalyseResult.size(); i++) {
			Quaternary quaternary = semanticAnalyseResult.get(i);
			String caculator = quaternary.getCaculator();
			String obj1 = quaternary.getOb1();
			String obj2 = quaternary.getOb2();
			String obj3 = quaternary.getOb3();
			if (labelList.contains(i + "")) {
				finalCodeList.add("L" + i + ":");
			}
			if (obj1 != null && obj1.equals("true")) {
				finalCodeList.add("\t" + "MOV" + "\t" + "EAX" + " , " + "1");
				finalCodeList.add("\t" + "MOV" + "\t" + obj1 + " , " + "EAX");
			} else if (obj1 != null && obj1.equals("false")) {
				finalCodeList.add("\t" + "MOV" + "\t" + "EAX" + " , " + "0");
				finalCodeList.add("\t" + "MOV" + "\t" + obj1 + " , " + "EAX");
			}

			if (obj2 != null && obj2.equals("true")) {
				finalCodeList.add("\t" + "MOV" + "\t" + "EAX" + " , " + "1");
				finalCodeList.add("\t" + "MOV" + "\t" + obj2 + " , " + "EAX");
			} else if (obj2 != null && obj2.equals("false")) {
				finalCodeList.add("\t" + "MOV" + "\t" + "EAX" + " , " + "0");
				finalCodeList.add("\t" + "MOV" + "\t" + obj2 + " , " + "EAX");
			}
			switch (caculator) {

			case "=":
				// 如果是数字
				if (Character.isDigit(obj1.charAt(0))) {
					finalCodeList.add("\t" + "MOV" + "\t" + "EAX" + " , " + obj1);
					finalCodeList.add("\t" + "MOV" + "\t" + obj3 + " , " + "EAX");
				}

				// 如果是字符
				else if (obj1.startsWith("'")) {
					finalCodeList.add("\t" + "MOV" + "\t" + "AL" + " , " + obj1);
					finalCodeList.add("\t" + "MOV" + "\t" + obj3 + " , " + "AL");
				} else if (obList.get(obj1).getType().equals("int")) {
					finalCodeList.add("\t" + "MOV" + "\t" + "EBX" + " , " + obj1);
					finalCodeList.add("\t" + "MOV" + "\t" + obj3 + " , " + "EBX");
				} else if (obList.get(obj1).getType().equals("bool")) {
					finalCodeList.add("\t" + "MOV" + "\t" + "AL" + " , " + obj1);
					finalCodeList.add("\t" + "MOV" + "\t" + obj3 + " , " + "AL");
				} else {
					finalCodeList.add("\t" + "MOV" + "\t" + "AL" + " , " + obj1);
					finalCodeList.add("\t" + "MOV" + "\t" + obj3 + " , " + "AL");
				}
				break;
			case "&&":

				finalCodeList.add("\t" + ".IF " + obj1 + "&&" + obj2);
				finalCodeList.add("\t" + "\t" + "MOV" + "\t" + obj3 + " , " + 1);
				finalCodeList.add("\t" + ".ELSE");
				finalCodeList.add("\t" + "\t" + "MOV" + "\t" + obj3 + " , " + 0);
				finalCodeList.add("\t" + ".ENDIF");

				break;
			case "||":

				finalCodeList.add("\t" + ".IF " + obj1 + "||" + obj2);
				finalCodeList.add("\t" + "\t" + "MOV" + "\t" + obj3 + " , " + 1);
				finalCodeList.add("\t" + ".ELSE");
				finalCodeList.add("\t" + "\t" + "MOV" + "\t" + obj3 + " , " + 0);
				finalCodeList.add("\t" + ".ENDIF");
				break;
			case "!=":
				finalCodeList.add("\t" + "MOV" + "\t" + "EAX" + " , " + obj1);
				finalCodeList.add("\t" + "MOV" + "\t" + "EBX" + " , " + obj2);

				finalCodeList.add("\t" + ".IF " + "EAX" + " != " + "EBX");
				finalCodeList.add("\t" + "\t" + "MOV" + "\t" + obj3 + " , " + 1);
				finalCodeList.add("\t" + ".ELSE");
				finalCodeList.add("\t" + "\t" + "MOV" + "\t" + obj3 + " , " + 0);
				finalCodeList.add("\t" + ".ENDIF");
				break;
			case "==":
				finalCodeList.add("\t" + "MOV" + "\t" + "EAX" + " , " + obj1);
				finalCodeList.add("\t" + "MOV" + "\t" + "EBX" + " , " + obj2);

				finalCodeList.add("\t" + ".IF " + "EAX" + " == " + "EBX");
				finalCodeList.add("\t" + "\t" + "MOV" + "\t" + obj3 + " , " + 1);
				finalCodeList.add("\t" + ".ELSE");
				finalCodeList.add("\t" + "\t" + "MOV" + "\t" + obj3 + " , " + 0);
				finalCodeList.add("\t" + ".ENDIF");
				break;
			case ">=":
				finalCodeList.add("\t" + "MOV" + "\t" + "EAX" + " , " + obj1);
				finalCodeList.add("\t" + "MOV" + "\t" + "EBX" + " , " + obj2);

				finalCodeList.add("\t" + ".IF " + "EAX" + " >= " + "EBX");
				finalCodeList.add("\t" + "\t" + "MOV" + "\t" + obj3 + " , " + 1);
				finalCodeList.add("\t" + ".ELSE");
				finalCodeList.add("\t" + "\t" + "MOV" + "\t" + obj3 + " , " + 0);
				finalCodeList.add("\t" + ".ENDIF");
				break;
			case "<=":
				finalCodeList.add("\t" + "MOV" + "\t" + "EAX" + " , " + obj1);
				finalCodeList.add("\t" + "MOV" + "\t" + "EBX" + " , " + obj2);

				finalCodeList.add("\t" + ".IF " + "EAX" + " <= " + "EBX");
				finalCodeList.add("\t" + "\t" + "MOV" + "\t" + obj3 + " , " + 1);
				finalCodeList.add("\t" + ".ELSE");
				finalCodeList.add("\t" + "\t" + "MOV" + "\t" + obj3 + " , " + 0);
				finalCodeList.add("\t" + ".ENDIF");
				break;
			case ">":
				finalCodeList.add("\t" + "MOV" + "\t" + "EAX" + " , " + obj1);
				finalCodeList.add("\t" + "MOV" + "\t" + "EBX" + " , " + obj2);

				finalCodeList.add("\t" + ".IF " + "EAX" + " > " + "EBX");
				finalCodeList.add("\t" + "\t" + "MOV" + "\t" + obj3 + " , " + 1);
				finalCodeList.add("\t" + ".ELSE");
				finalCodeList.add("\t" + "\t" + "MOV" + "\t" + obj3 + " , " + 0);
				finalCodeList.add("\t" + ".ENDIF");
				break;
			case "<":
				finalCodeList.add("\t" + "MOV" + "\t" + "EAX" + " , " + obj1);
				finalCodeList.add("\t" + "MOV" + "\t" + "EBX" + " , " + obj2);

				finalCodeList.add("\t" + ".IF " + "EAX" + " < " + "EBX");
				finalCodeList.add("\t" + "\t" + "MOV" + "\t" + obj3 + " , " + 1);
				finalCodeList.add("\t" + ".ELSE");
				finalCodeList.add("\t" + "\t" + "MOV" + "\t" + obj3 + " , " + 0);
				finalCodeList.add("\t" + ".ENDIF");
				break;

			case "+":
				finalCodeList.add("\t" + "MOV" + "\t" + "EAX" + " , " + obj1);
				finalCodeList.add("\t" + "ADD" + "\t" + "EAX" + " , " + obj2);
				finalCodeList.add("\t" + "MOV" + "\t" + obj3 + " , " + "EAX");
				break;
			case "-":
				finalCodeList.add("\t" + "MOV" + "\t" + "EAX" + " , " + obj1);
				finalCodeList.add("\t" + "SUB" + "\t" + "EAX" + " , " + obj2);
				finalCodeList.add("\t" + "MOV" + "\t" + obj3 + " , " + "EAX");
				break;
			case "*":
				finalCodeList.add("\t" + "MOV" + "\t" + "EAX" + " , " + obj1);
				finalCodeList.add("\t" + "MUL" + "\t" + obj2);
				finalCodeList.add("\t" + "MOV" + "\t" + obj3 + " , " + "EAX");
				break;
			case "/":
				finalCodeList.add("\t" + "MOV" + "\t" + "EDX" + " , " + 0); // 存放余数
				finalCodeList.add("\t" + "MOV" + "\t" + "EAX" + " , " + obj1); // 被除数
				finalCodeList.add("\t" + "MOV" + "\t" + "ECX" + " , " + obj2); // 除数
				finalCodeList.add("\t" + "DIV" + "\t" + "ECX"); // 除数
				finalCodeList.add("\t" + "MOV" + "\t" + obj3 + " , " + "EAX"); // EAX为商，EDX为余数
				break;
			case "%":
				finalCodeList.add("\t" + "MOV" + "\t" + "EDX" + " , " + 0); // 存放余数
				finalCodeList.add("\t" + "MOV" + "\t" + "EAX" + " , " + obj1); // 被除数
				finalCodeList.add("\t" + "MOV" + "\t" + "ECX" + " , " + obj2); // 除数
				finalCodeList.add("\t" + "DIV" + "\t" + "ECX"); // 除数
				finalCodeList.add("\t" + "MOV" + "\t" + obj3 + " , " + "EDX"); // EAX为商，EDX为余数
				break;
			case "FJ":
				finalCodeList.add("\t" + "MOV" + "\t" + "AL" + " , " + obj2);
				finalCodeList.add("\t" + "CMP" + "\t" + "AL" + " , " + 1);
				finalCodeList.add("\t" + "JNZ" + "\t" + "L" + obj1);
				break;
			case "RJ":
				finalCodeList.add("\t" + "JMP" + "\t" + "L" + obj1);
				break;
			case "printf":
				finalCodeList.add("\t" + "INVOKE StdOut, addr " + obj1);
				break;
			case "scanf":
				finalCodeList.add("\t" + "INVOKE StdIn, addr " + obj1 + ", sizeof " + obj1);
				break;
			default:
				break;
			}
		}
		if (labelList.contains(semanticAnalyseResult.size() + "")) {
			finalCodeList.add("L" + semanticAnalyseResult.size() + ":");
		}
		finalCodeList.add("main ENDP");
		finalCodeList.add("INVOKE ExitProcess,0");

		finalCodeList.add("END main"); // 主函数结尾
	}

	public ArrayList<String> getFinalCodeList() {
		return finalCodeList;
	}
}
