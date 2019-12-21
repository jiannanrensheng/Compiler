package Complier;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

// 单词类
class Word { // 单词的结构
	private String value;
	private int code; // 为了与非终结符统一，因此定义为String类型

	public Word(String value, int code) {
		this.value = value;
		this.code = code;
	}

	public String getValue() {
		return this.value;
	}

	public int getCode() {
		return this.code;
	}
}

//

public class WordAnalyse {
	static private String str = "";
	static private ArrayList<Word> result;

	public WordAnalyse(String fileName) throws Exception {
		try {
			File file = new File(fileName);
			// 读文件，转化成字符串
			BufferedReader br = new BufferedReader(new FileReader(file));
			String s = "";
			while ((s = br.readLine()) != null) {
				str += s + "\n";
			}
			this.result = new ArrayList<Word>();
		} catch (Exception e) {
			throw new Exception("目标文件不存在或无法打开");
		}

	}

	// 词法分析，将结果放入HashMap键值对中
	public ArrayList<Word> WordAnalyse() throws Exception {
		for (int i = 0; i < str.length();) {
			// 如果碰到换行符，则继续往下读取字符串
			// 判断编译预处理头#include<stdio.h>
			if (i + 16 < str.length()) {
				if (str.substring(i, i + 17).equals("#include<stdio.h>")) {
					// 将单词即识别得到的编号放入HashMap中
					result.add(new Word("#include<stdio.h>", 0));
					i += 17;
					continue;
				}
			}
			// 判断单词长度为6的单词
			if (i + 5 < str.length()) {
				if (str.substring(i, i + 6).equals("printf")) {
					if (i + 6 >= str.length() || !Character.isDigit(str.charAt(i + 6))
							|| !Character.isLetter(str.charAt(i + 6)) || str.charAt(i + 6) != '_') {
						result.add(new Word("printf", 0));
						i += 6;
						continue;
					}
				}
			}
			// 判断长度为5的单词,while和scanf（要注意其后或者为字符串终结符或者为非字母或者非下划线）
			if (i + 4 < str.length()) {
				String s = str.substring(i, i + 5);
				switch (s) {
				case "while": {
					if (i + 5 >= str.length() || !Character.isDigit(str.charAt(i + 5))
							|| !Character.isLetter(str.charAt(i + 5)) || str.charAt(i + 5) != '_') {
						result.add(new Word(s, 0));
						i += 5;
						continue;
					}
					break;
				}
				case "scanf": {
					if (i + 5 >= str.length() || !Character.isDigit(str.charAt(i + 5))
							|| !Character.isLetter(str.charAt(i + 5)) || str.charAt(i + 5) != '_') {
						result.add(new Word(s, 0));
						i += 5;
						continue;
					}
					break;
				}
				case "false": {
					if (i + 5 >= str.length() || !Character.isDigit(str.charAt(i + 5))
							|| !Character.isLetter(str.charAt(i + 5)) || str.charAt(i + 5) != '_') {
						result.add(new Word(s, 0));
						i += 5;
						continue;
					}
					break;
				}
				default:
					break;
				}
			}
			// 判断长度为4的单词,char和else

			if (i + 3 < str.length()) {
				String s = str.substring(i, i + 4);
				switch (s) {
				case "void": {
					if (i + 4 >= str.length() || !Character.isDigit(str.charAt(i + 4))
							|| !Character.isLetter(str.charAt(i + 4)) || str.charAt(i + 4) != '_') {
						result.add(new Word(s, 0));
						i += 4;
						continue;
					}
					break;
				}
				case "main": {
					if (i + 4 >= str.length() || !Character.isDigit(str.charAt(i + 4))
							|| !Character.isLetter(str.charAt(i + 4)) || str.charAt(i + 4) != '_') {
						result.add(new Word(s, 0));
						i += 4;
						continue;
					}
					break;
				}
				case "char": {
					if (i + 4 >= str.length() || !Character.isDigit(str.charAt(i + 4))
							|| !Character.isLetter(str.charAt(i + 4)) || str.charAt(i + 4) != '_') {
						result.add(new Word(s, 0));
						i += 4;
						continue;
					}
					break;
				}
				case "else": {
					if (i + 4 >= str.length() || !Character.isDigit(str.charAt(i + 4))
							|| !Character.isLetter(str.charAt(i + 4)) || str.charAt(i + 4) != '_') {
						result.add(new Word("else", 0));
						i += 4;
						continue;
					}
				}
				case "true": {
					if (i + 4 >= str.length() || !Character.isDigit(str.charAt(i + 4))
							|| !Character.isLetter(str.charAt(i + 4)) || str.charAt(i + 4) != '_') {
						result.add(new Word(s, 0));
						i += 4;
						continue;
					}
					break;
				}
				case "bool": {
					if (i + 4 >= str.length() || !Character.isDigit(str.charAt(i + 4))
							|| !Character.isLetter(str.charAt(i + 4)) || str.charAt(i + 4) != '_') {
						result.add(new Word(s, 0));
						i += 4;
						continue;
					}
					break;
				}
				default:
					break;
				}
			}
			// 判断长度为3的单词,char和else
			if (i + 2 < str.length()) {
				String s = str.substring(i, i + 3);
				switch (s) {
				case "int": {
					if (i + 3 >= str.length() || !Character.isDigit(str.charAt(i + 3))
							|| !Character.isLetter(str.charAt(i + 3)) || str.charAt(i + 3) != '_') {
						result.add(new Word("int", 0));
						i += 3;
						continue;
					}
					break;
				}
				case "for": {
					if (i + 3 >= str.length() || !Character.isDigit(str.charAt(i + 3))
							|| !Character.isLetter(str.charAt(i + 3)) || str.charAt(i + 3) != '_') {
						result.add(new Word("for", 0));
						i += 3;
						continue;
					}
					break;
				}
				default:
					break;
				}
			}
			// 判断长度为2的单词,if,+=,-=,*=,/=,%=,++,--,||，&&
			if (i + 1 < str.length()) {
				String s = str.substring(i, i + 2);
				if (s.equals("if")) {
					if (i + 2 >= str.length() || !Character.isDigit(str.charAt(i + 2))
							|| !Character.isLetter(str.charAt(i + 2)) || str.charAt(i + 2) != '_') {
						result.add(new Word("if", 0));
						i += 2;
						continue;
					}
				} else {
					switch (s) {
					case "+=": {
						result.add(new Word(s, 0));
						i += 2;
						continue;
					}
					case "-=": {
						result.add(new Word(s, 0));
						i += 2;
						continue;
					}
					case "*=": {
						result.add(new Word(s, 0));
						i += 2;
						continue;
					}
					case "/=": {
						result.add(new Word(s, 0));
						i += 2;
						continue;
					}
					case "%=": {
						result.add(new Word(s, 0));
						i += 2;
						continue;
					}
					case "++": {
						result.add(new Word(s, 0));
						i += 2;
						continue;
					}
					case "--": {
						result.add(new Word(s, 0));
						i += 2;
						continue;
					}
					case "||": {
						result.add(new Word(s, 0));
						i += 2;
						continue;
					}
					case "&&": {
						result.add(new Word(s, 0));
						i += 2;
						continue;
					}
					case ">=": {
						result.add(new Word(s, 0));
						i += 2;
						continue;
					}
					case "<=": {
						result.add(new Word(s, 0));
						i += 2;
						continue;
					}
					case "==": {
						result.add(new Word(s, 0));
						i += 2;
						continue;
					}
					case "!=": {
						result.add(new Word(s, 0));
						i += 2;
						continue;
					}
					case "//": {
						int j = i + 2;
						while (j < str.length() && str.charAt(j) == '\n') {
							j++;
						}
						i = j;
						continue;
					}
					case "/*": {
						int j = i + 2;
						while (j < str.length()) {
							if (str.charAt(j) == '*') {
								if (j + 1 < str.length() && str.charAt(j + 1) == '/') {
									j += 2;
									break;
								}
							}
							j++;
						}
						if (j >= str.length()) {
							throw new Exception("注释方法错误");
						} else {
							i = j;
							continue;
						}
					}
					default:
						break;
					}
				}
			}
			String s = str.substring(i, i + 1);
			switch (s) {
			case " ":
			case "\t":
			case "\n": {
				i++;
				continue;
			}
			// 注意这里可能出现正数

			case "+": {
				i++;
				result.add(new Word(s, 0));
				continue;
				// int j = i + 1;
				// // 因为一个正常数可以是一个正号加上若干个空格再加上数字,因此需要将这些空格去掉
				// if (result.get(result.size() - 1).getCode() != 2 &&
				// result.get(result.size() - 1).getCode() != 1) {
				// while (j < str.length()
				// && (str.charAt(j) == ' ' || str.charAt(j) == '\t' ||
				// str.charAt(j) == '\n')) {
				// j++;
				// }
				// // 如果已经达到了字符串的终结符，则直接识别为正号
				// if (j >= str.length()) {
				// i++;
				// result.add(new Word(s, 0));
				// continue;
				// }
				//
				// else {
				// int k = j;
				// while (j < str.length() && Character.isDigit(str.charAt(j)))
				// {
				// j++;
				// }
				// // 如果k大小不变，要么已经超过字符串终结符的位置，要么就是未识别到数字
				// if (k == j) {
				// i++;
				// result.add(new Word(s, 0));
				// continue;
				// } else {
				// result.add(new Word(str.substring(i, j), 2));
				// i = j;
				// continue;
				// }
				// }
				// } else {
				// i++;
				// result.add(new Word(s, 0));
				// continue;
				// }
			}
			// 注意这里可能出现负数
			case "-": {
				i++;
				result.add(new Word(s, 0));
				continue;
				// int j = i + 1;
				// // 因为一个负常数可以是一个负号加上若干个空格再加上数字,因此需要将这些空格去掉
				// if (result.get(result.size() - 1).getCode() != 2 &&
				// result.get(result.size() - 1).getCode() != 1) {
				// while (j < str.length()
				// && (str.charAt(j) == ' ' || str.charAt(j) == '\t' ||
				// str.charAt(j) == '\n')) {
				// j++;
				// }
				// // 如果已经达到了字符串的终结符，则直接识别为负号
				// if (j >= str.length()) {
				// i++;
				// result.add(new Word(s, 0));
				// continue;
				// }
				//
				// else {
				// int k = j;
				// while (j < str.length() && Character.isDigit(str.charAt(j)))
				// {
				// j++;
				// }
				// // 如果k大小不变，要么已经超过字符串终结符的位置，要么就是未识别到数字
				// if (k == j) {
				// i++;
				// result.add(new Word(s, 0));
				// continue;
				// } else {
				// result.add(new Word(str.substring(i, j), 2));
				// i = j;
				// continue;
				// }
				// }
				// } else {
				// i++;
				// result.add(new Word(s, 0));
				// continue;
				// }
			}
			case "*": {
				i++;
				result.add(new Word(s, 0));
				continue;
			}
			// 这里要判断是不是注释符//或/* */
			case "/": {
				i++;
				result.add(new Word(s, 0));
				continue;
			}
			case "%": {
				i++;
				result.add(new Word(s, 0));
				continue;
			}
			case "!": {
				i++;
				result.add(new Word("!", 0));
				continue;
			}
			case "{": {
				i++;
				result.add(new Word("{", 0));
				continue;
			}
			case "}": {
				i++;
				result.add(new Word("}", 0));
				continue;
			}
			case "(": {
				i++;
				result.add(new Word("(", 0));
				continue;
			}
			case ")": {
				i++;
				result.add(new Word(")", 0));
				continue;
			}
			case ",": {
				i++;
				result.add(new Word(",", 0));
				continue;
			}
			case ";": {
				i++;
				result.add(new Word(";", 0));
				continue;
			}
			case "=": {
				i++;
				result.add(new Word("=", 0));
				continue;
			}
			case ">": {
				i++;
				result.add(new Word(">", 0));
				continue;
			}
			case "<": {
				i++;
				result.add(new Word("<", 0));
				continue;
			}
			// 判断双引号主要为判断字符串
			case "\"": {
				int j = i + 1;
				while (j < str.length() && str.charAt(j) != '"') {
					j++;
				}
				// 不允许双引号后没有对应的单引号配对或者为空字符
				if (j >= str.length()) {
					throw new Exception("缺少配对的双引号");
				} else {
					if (str.substring(i + 1, j).equals("%d") || str.substring(i + 1, j).equals("%c")) {
						result.add(new Word(str.substring(i + 1, j), 4));
						i = j + 1;
						continue;
					} else {
						throw new Exception("非法字符串");
					}

				}
			}
			case "'": {
				int j = i + 1;
				while (j < str.length() && str.charAt(j) != '\'') {
					j++;
				}
				// 不允许单引号后没有对应的单引号配对或者为空字符
				if (j >= str.length()) {
					throw new Exception("缺少配对的单引号");
				} else if (j == i + 1) {
					throw new Exception("空字符常量非法");
				}
				// 字符只能占一个位置（这里不考虑字符编码表示的问题）
				else if (j == i + 2) {
					// 保存对应的字符常量
					result.add(new Word(str.substring(i + 1, j), 3));
					i = j + 1;
					continue;
				} else {
					throw new Exception("字符常量不能由多个字符组成");
				}
			}
			case "&": {
				i++;
				result.add(new Word("&", 0));
				continue;
			}
			default: {

				if (Character.isLetter(str.charAt(i)) || str.charAt(i) == '_') {
					int j = i;
					j += 1;
					// 以下划线或字母开头的，由数字、字母、下划线组成的为标识符
					while (j < str.length() && (str.charAt(j) == '_' || Character.isLetter(str.charAt(j))
							|| Character.isDigit(str.charAt(j)) || str.charAt(j) == '_')) {
						j++;
					}
					result.add(new Word(str.substring(i, j), 1));
					i = j;
					continue;
				}
				// 碰到字母或者下划线则直接报错为非法标识符，否则碰到非数字的字符直接结束循环判断判定为标识符
				else if (Character.isDigit(str.charAt(i))) {
					int j = i;
					j += 1;
					while (j < str.length() && Character.isDigit(str.charAt(j))) {
						j++;
					}
					if (j < str.length() && (Character.isLetter(str.charAt(j)) || str.charAt(j) == '_')) {
						throw new Exception("非法标识符");
					} else {
						// 否则为数字
						result.add(new Word(str.substring(i, j), 2));
						i = j;
						continue;
					}
				}
			}
			}
			throw new Exception("词法错误");
		}
		return result;
	}

}
