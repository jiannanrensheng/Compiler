package Complier;

import java.util.ArrayList;

public class GrammarAnalyse {
	ArrayList<Word> wordAnalyseResult = new ArrayList<Word>();
	static int index;
	static Word token;
	ArrayList<String> grammarAnalyseResult = new ArrayList<String>();

	public GrammarAnalyse(ArrayList<Word> wordAnalyseResult) {
		this.wordAnalyseResult = wordAnalyseResult;
		this.wordAnalyseResult.add(new Word("#", 0));
		index = -1;
	}

	public ArrayList<String> getGrammarAnalyseResult() {
		return grammarAnalyseResult;
	}

	public void getToken() {
		index++;
		token = wordAnalyseResult.get(index);
	}

	// 标识符
	public void i() throws Exception {
		int code = token.getCode();
		if (code == 1) {
			getToken();
		} else {
			System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
			throw new Exception("语法错误");
		}
	}

	// 数字常量
	public void d() throws Exception {
		int code = token.getCode();
		if (code == 2) {
			getToken();
		} else {
			System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
			throw new Exception("语法错误");
		}
	}

	// 字符常量
	public void c() throws Exception {
		int code = token.getCode();
		if (code == 3) {
			getToken();
		} else {
			System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
			throw new Exception("语法错误");
		}
	}

	// 字符串常量
	public void s() throws Exception {
		int code = token.getCode();
		if (code == 4) {
			getToken();
		} else {
			System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
			throw new Exception("语法错误");
		}
	}

	// 布尔类型常量
	public void b() throws Exception {
		String val = token.getValue();
		int code = token.getCode();
		if (code == 0 && (val.equals("true") || val.equals("false"))) {
			getToken();
		} else {
			System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
			throw new Exception("语法错误");
		}
	}

	// 常量（整型，字符型，布尔型）
	public void W() throws Exception {
		String val = token.getValue();
		int code = token.getCode();
		if (code == 0 && (val.equals("false") || val.equals("false"))) {
			grammarAnalyseResult.add("W -> b");
			b();
		} else if (code == 2) {
			grammarAnalyseResult.add("W -> d");
			d();
		} else if (code == 3) {
			grammarAnalyseResult.add("W -> c");
			c();
		} else {
			System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
			throw new Exception("语法错误");
		}

	}

	// 语法分析
	public void analyse() throws Exception {
		S();
	}

	// 文法开始（头文件部分）
	public void S() throws Exception {
		getToken();
		if (token.getCode() == 0 && token.getValue().equals("#include<stdio.h>")) {
			getToken();
		} else {
			System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
			throw new Exception("语法错误");
		}
		S0();
		if (token.getCode() == 0 && token.getValue().equals("#")) {

		} else {
			System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
			throw new Exception("语法错误");
		}
	}

	// int main（void）或void main()主函数
	public void S0() throws Exception {
		if (token.getCode() == 0) {
			switch (token.getValue()) {
			case "int": {
				grammarAnalyseResult.add("S0 -> int main( void ) { S1 }");
				getToken();
				if (token.getCode() == 0 && token.getValue().equals("main")) {
					getToken();
				} else {
					System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
					throw new Exception("语法错误");
				}
				if (token.getCode() == 0 && token.getValue().equals("(")) {
					getToken();
				} else {
					System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
					throw new Exception("语法错误");
				}
				if (token.getCode() == 0 && token.getValue().equals("void")) {
					getToken();
				} else {
					System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
					throw new Exception("语法错误");
				}
				if (token.getCode() == 0 && token.getValue().equals(")")) {
					getToken();
				} else {
					System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
					throw new Exception("语法错误");
				}
				if (token.getCode() == 0 && token.getValue().equals("{")) {
					getToken();
				} else {
					System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
					throw new Exception("语法错误");
				}
				S1();
				if (token.getCode() == 0 && token.getValue().equals("}")) {
					getToken();
				} else {
					System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
					throw new Exception("语法错误");
				}
				if (token.getCode() == 0 && token.getValue().equals("#")) {

				} else {
					System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
					throw new Exception("语法错误");
				}
				break;
			}
			case "void":
				grammarAnalyseResult.add("S0 -> void main ( ) { S1 }");
				if (token.getCode() == 0 && token.getValue().equals("main")) {
					getToken();
				} else {
					System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
					throw new Exception("语法错误");
				}
				if (token.getCode() == 0 && token.getValue().equals("(")) {
					getToken();
				} else {
					System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
					throw new Exception("语法错误");
				}
				if (token.getCode() == 0 && token.getValue().equals(")")) {
					getToken();
				} else {
					System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
					throw new Exception("语法错误");
				}
				if (token.getCode() == 0 && token.getValue().equals("{")) {
					getToken();
				} else {
					System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
					throw new Exception("语法错误");
				}
				S1();
				if (token.getCode() == 0 && token.getValue().equals("}")) {
					getToken();
				} else {
					System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
					throw new Exception("语法错误");
				}
				break;
			}
		} else {
			System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
			throw new Exception("语法错误");
		}
	}

	// 程序块
	public void S1() throws Exception {
		if (token.getCode() == 0) {
			switch (token.getValue()) {
			case "int":
			case "char":
			case "bool":
			case "if":
			case "while":
			case "for":
			case "printf":
			case "scanf":
			case ";":
			case "{":
				grammarAnalyseResult.add("S1 -> A S1");
				A();
				S1();
				break;
			case "}":
				grammarAnalyseResult.add("S1 -> $");
				break;
			default:
				System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
				throw new Exception("语法错误");
			}
		} else if (token.getCode() == 1) {
			grammarAnalyseResult.add("S1 -> AS1");
			A();
			S1();
		}
	}

	// 程序块的成分，包括定义语句，赋值语句，if-else语句，while语句，for语句，printf语句，scanf语句
	public void A() throws Exception {
		if (token.getCode() == 0) {
			switch (token.getValue()) {
			case "int":
			case "char":
			case "bool":
				grammarAnalyseResult.add("A -> B");
				B();
				break;
			case "if":
				grammarAnalyseResult.add("A -> D");
				D();
				break;
			case "while":
				grammarAnalyseResult.add("A -> E");
				E();
				break;
			case "for":
				grammarAnalyseResult.add("A -> F");
				F();
				break;
			case "printf":
				grammarAnalyseResult.add("A -> G");
				G();
				break;
			case "scanf":
				grammarAnalyseResult.add("A -> G");
				G();
				break;
			case ";":
				grammarAnalyseResult.add("A -> ;");
				getToken();
				break;
			case "{":
				grammarAnalyseResult.add("A -> { S1 }");
				getToken();
				S1();
				if (token.getCode() == 0 && token.getValue().equals("}")) {
					getToken();
				} else {
					System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
					throw new Exception("语法错误");

				}
				break;
			default:
				System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
				throw new Exception("语法错误");
			}
		} else if (token.getCode() == 1) {
			grammarAnalyseResult.add("A -> C");
			C();
		} else {
			System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
			throw new Exception("语法错误");
		}
	}

	// 定义语句，可以赋值
	public void B() throws Exception {
		B0();
		B1();
		if (token.getCode() == 0 && token.getValue().equals(";")) {
			getToken();
		} else {
			System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
			throw new Exception("语法错误");
		}
	}

	public void B0() throws Exception {
		if (token.getCode() == 0) {
			switch (token.getValue()) {
			case "int":
			case "char":
			case "bool":
				grammarAnalyseResult.add("B0 -> int | char | bool");
				getToken();
				break;
			default:
				System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
				throw new Exception("语法错误");
			}
		} else {
			System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
			throw new Exception("语法错误");
		}
	}

	public void B1() throws Exception {
		if (token.getCode() == 1) {
			grammarAnalyseResult.add("B1 -> i B2");
			i();
			B2();
		} else {
			System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
			throw new Exception("语法错误");
		}
	}

	public void B2() throws Exception {
		if (token.getCode() == 0) {
			switch (token.getValue()) {
			case "=":
				grammarAnalyseResult.add("B2 -> = N");
				getToken();
				N();
				break;
			case ";":
				grammarAnalyseResult.add("B2 -> $");
				break;
			default:
				System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
				throw new Exception("语法错误");
			}
		} else {
			System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
			throw new Exception("语法错误");
		}
	}

	public void C() throws Exception {
		if (token.getCode() == 1) {
			grammarAnalyseResult.add("C -> i C0 ; ");
			getToken();
			C0();
			if (token.getValue().equals(";")) {
				getToken();
			} else {
				System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
				throw new Exception();
			}
		} else {
			System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
			throw new Exception("语法错误");
		}
	}

	public void C0() throws Exception {
		if (token.getCode() == 0) {
			switch (token.getValue()) {
			case "=":
				grammarAnalyseResult.add("C0 -> = N");
				getToken();
				N();
				break;
			case "+=":
				grammarAnalyseResult.add("C0 -> += N");
				getToken();
				N();
				break;
			case "-=":
				grammarAnalyseResult.add("C0 -> -= N");
				getToken();
				N();
				break;
			case "*=":
				grammarAnalyseResult.add("C0 -> *= N");
				getToken();
				N();
				break;
			case "/=":
				grammarAnalyseResult.add("C0 -> /= N");
				getToken();
				N();
				break;
			case "%=":
				grammarAnalyseResult.add("C0 -> %= N");
				getToken();
				N();
				break;
			case "++":
				grammarAnalyseResult.add("C0 -> ++");
				getToken();
				break;
			case "--":
				grammarAnalyseResult.add("C0 -> --");
				getToken();
				break;
			default:
				System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
				throw new Exception("语法错误");
			}
		} else {
			System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
			throw new Exception("语法错误");
		}

	}

	// 表达式（包含算术表达式和布尔表达式）
	public void N() throws Exception {
		grammarAnalyseResult.add("N -> N1 N0");
		N1();
		N0();
	}

	public void N0() throws Exception {
		if (token.getCode() == 0) {
			switch (token.getValue()) {
			case "||":
				grammarAnalyseResult.add("N0 -> || N1 N0");
				getToken();
				N1();
				N0();
				break;
			case ")":
			case ";":
				grammarAnalyseResult.add("N0 -> $");
				break;
			default:
				System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
				throw new Exception("语法错误");
			}
		} else {
			System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
			throw new Exception("语法错误");
		}
	}

	public void N1() throws Exception {
		grammarAnalyseResult.add("N1 -> N3 N2");
		N3();
		N2();
	}

	public void N2() throws Exception {
		if (token.getCode() == 0) {
			switch (token.getValue()) {
			case "&&":
				grammarAnalyseResult.add("N2 -> && N3 N2");
				getToken();
				N3();
				N2();
				break;
			case ")":
			case ";":
			case "||":
				grammarAnalyseResult.add("N2 -> $");
				break;
			default:
				System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
				throw new Exception("语法错误");
			}
		} else {
			System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
			throw new Exception("语法错误");
		}
	}

	public void N3() throws Exception {
		grammarAnalyseResult.add("N3 -> N5 N4");
		N5();
		N4();
	}

	// == 和 ！=
	public void N4() throws Exception {
		if (token.getCode() == 0) {
			switch (token.getValue()) {
			case "==":
				grammarAnalyseResult.add("N4 -> == N5 N4");
				getToken();
				N5();
				N4();
				break;
			case "!=":
				grammarAnalyseResult.add("N4 -> != N5 N4");
				getToken();
				N5();
				N4();
				break;
			case ")":
			case ";":
			case "||":
			case "&&":
				grammarAnalyseResult.add("N4 -> $");
				break;
			default:
				System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
				throw new Exception("语法错误");
			}
		} else {
			System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
			throw new Exception("语法错误");
		}
	}

	public void N5() throws Exception {
		grammarAnalyseResult.add("N5 -> N7 N6");
		N7();
		N6();
	}

	// >= <= > <
	public void N6() throws Exception {
		if (token.getCode() == 0) {
			switch (token.getValue()) {
			case ">=":
				grammarAnalyseResult.add("N6 -> >= N7 N6");
				getToken();
				N7();
				N6();
				break;
			case "<=":
				grammarAnalyseResult.add("N6 -> <= N7 N6");
				getToken();
				N7();
				N6();
				break;
			case ">":
				grammarAnalyseResult.add("N6 -> > N7 N6");
				getToken();
				N7();
				N6();
				break;
			case "<":
				grammarAnalyseResult.add("N6 -> < N7 N6");
				getToken();
				N7();
				N6();
				break;
			case ")":
			case ";":
			case "||":
			case "&&":
			case "==":
			case "!=":
				grammarAnalyseResult.add("N6 -> $");
				break;
			default:
				System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
				throw new Exception("语法错误");
			}
		} else {
			System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
			throw new Exception("语法错误");
		}
	}

	public void N7() throws Exception {
		grammarAnalyseResult.add("N7 -> N8 N9");
		N9();
		N8();
	}

	public void N8() throws Exception {
		if (token.getCode() == 0) {
			switch (token.getValue()) {
			case "+":
				grammarAnalyseResult.add("N8 -> + N9 N8");
				getToken();
				N9();
				N8();
				break;
			case "-":
				grammarAnalyseResult.add("N8 -> - N9 N8");
				getToken();
				N9();
				N8();
				break;

			case ")":
			case ";":
			case "||":
			case "&&":
			case "==":
			case "!=":
			case ">=":
			case "<=":
			case ">":
			case "<":
				grammarAnalyseResult.add("N8 -> $");
				break;
			default:
				System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
				throw new Exception("语法错误");
			}
		} else {
			System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
			throw new Exception("语法错误");
		}
	}

	public void N9() throws Exception {
		grammarAnalyseResult.add("N9 -> N11 N10");
		N11();
		N10();
	}

	public void N10() throws Exception {
		if (token.getCode() == 0) {
			switch (token.getValue()) {
			case "*":
				grammarAnalyseResult.add("N10 -> * N11 N10");
				getToken();
				N11();
				N10();
				break;
			case "/":
				grammarAnalyseResult.add("N10 -> / N11 N10");
				getToken();
				N11();
				N10();
				break;
			case "%":
				grammarAnalyseResult.add("N10 -> % N11 N10");
				getToken();
				N11();
				N10();
				break;

			case ")":
			case ";":
			case "||":
			case "&&":
			case "==":
			case "!=":
			case ">=":
			case "<=":
			case ">":
			case "<":
			case "+":
			case "-":
				grammarAnalyseResult.add("N8 -> $");
				break;
			default:
				System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
				throw new Exception("语法错误");
			}
		} else {
			System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
			throw new Exception("语法错误");
		}
	}

	public void N11() throws Exception {
		if (token.getCode() == 0) {
			switch (token.getValue()) {
			case "(":
				grammarAnalyseResult.add("N11 -> ( N )");
				getToken();
				N();
				if (token.getCode() == 0 && token.getValue().equals(")")) {
					getToken();
				} else {
					System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
					throw new Exception("语法错误");
				}
				break;
			case "false":
			case "true":
				b();
				break;
			default:
				System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
				throw new Exception("语法错误");
			}
		} else if (token.getCode() == 1) {
			i();
			N12();
		} else if (token.getCode() == 2) {
			d();
		} else if (token.getCode() == 3) {
			c();
		} else {
			System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
			throw new Exception("语法错误");
		}

	}

	public void N12() throws Exception {
		if (token.getCode() == 0) {
			switch (token.getValue()) {
			case "++":
				grammarAnalyseResult.add("N12 -> ++");
				getToken();
				break;
			case "--":
				grammarAnalyseResult.add("N12 -> --");
				getToken();
				break;
			case ")":
			case ";":
			case "||":
			case "&&":
			case "==":
			case "!=":
			case ">=":
			case "<=":
			case ">":
			case "<":
			case "+":
			case "-":
			case "*":
			case "/":
			case "%":
				grammarAnalyseResult.add("N12 -> $");
				break;
			default:
				System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
				throw new Exception("语法错误");
			}
		}
	}

	public void D() throws Exception {
		if (token.getCode() == 0 && token.getValue().equals("if")) {
			grammarAnalyseResult.add("D -> if ( N ) A D0");
			getToken();
			if (token.getCode() == 0 && token.getValue().equals("(")) {
				getToken();
				N();
				if (token.getCode() == 0 && token.getValue().equals(")")) {
					getToken();
					A();
					D0();
				} else {
					System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
					throw new Exception("语法错误");
				}
			} else {
				System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
				throw new Exception("语法错误");
			}
		} else {
			System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
			throw new Exception("语法错误");
		}
	}

	public void D0() throws Exception {
		if (token.getCode() == 0) {
			switch (token.getValue()) {
			case "else":
				grammarAnalyseResult.add("D0 -> else A");
				getToken();
				A();
				break;
			case "int":
			case "char":
			case "bool":
			case "if":
			case "while":
			case "for":
			case "printf":
			case "scanf":
			case ";":
			case "{":
			case "}":
				grammarAnalyseResult.add("D0 -> $");
				break;
			default:
				System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
				throw new Exception("语法错误");
			}
		} else if (token.getCode() == 1) {
			grammarAnalyseResult.add("D0 -> $");
		} else {
			System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
			throw new Exception("语法错误");
		}
	}

	public void E() throws Exception {
		if (token.getCode() == 0 && token.getValue().equals("while")) {
			grammarAnalyseResult.add("E -> while ( N ) A");
			getToken();
			if (token.getCode() == 0 && token.getValue().equals("(")) {
				getToken();
				N();
				if (token.getCode() == 0 && token.getValue().equals(")")) {
					getToken();
					A();
				} else {
					System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
					throw new Exception("语法错误");
				}
			} else {
				System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
				throw new Exception("语法错误");
			}
		} else {
			System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
			throw new Exception("语法错误");
		}
	}

	// for语句
	public void F() throws Exception {
		if (token.getCode() == 0 && token.getValue().equals("for")) {
			grammarAnalyseResult.add("F -> for ( i C0 ; N ; i C0 ) A");
			getToken();
			if (token.getCode() == 0 && token.getValue().equals("(")) {
				getToken();
				C();
				N();
				if (token.getCode() == 0 && token.getValue().equals(";")) {
					getToken();
					i();
					C0();
					if (token.getCode() == 0 && token.getValue().equals(")")) {
						getToken();
						A();
					} else {
						System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
						throw new Exception("语法错误");
					}

				} else {
					System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
					throw new Exception("语法错误");
				}

			} else {
				System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
				throw new Exception("语法错误");
			}
		} else {
			System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
			throw new Exception("语法错误");
		}
	}

	public void G() throws Exception {
		grammarAnalyseResult.add("G -> G0 ;");
		G0();
		if (token.getCode() == 0 && token.getValue().equals(";")) {
			getToken();
		} else {
			System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
			throw new Exception("语法错误");
		}
	}

	public void G0() throws Exception {
		if (token.getCode() == 0) {
			switch (token.getValue()) {
			case "printf":
				G1();
				break;
			case "scanf":
				G2();
				break;
			default:
				System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
				throw new Exception("语法错误");
			}
		} else {
			System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
			throw new Exception("语法错误");
		}

	}

	public void G1() throws Exception {
		if (token.getCode() == 0 && token.getValue().equals("printf")) {
			grammarAnalyseResult.add("G1 -> printf ( s , i ) ;");
			getToken();
			if (token.getCode() == 0 && token.getValue().equals("(")) {
				getToken();
				if (token.getCode() == 4) {
					getToken();
					if (token.getCode() == 0 && token.getValue().equals(",")) {
						getToken();
						i();
						if (token.getCode() == 0 && token.getValue().equals(")")) {
							getToken();
						} else {
							System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
							throw new Exception("语法错误");
						}
					} else {
						System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
						throw new Exception("语法错误");
					}
				} else {
					System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
					throw new Exception("语法错误");
				}
			} else {
				System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
				throw new Exception("语法错误");
			}
		} else {
			System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
			throw new Exception("语法错误");
		}
	}

	public void G2() throws Exception {
		if (token.getCode() == 0 && token.getValue().equals("scanf")) {
			grammarAnalyseResult.add("G2 -> scanf ( s , & i ) ;");
			getToken();
			if (token.getCode() == 0 && token.getValue().equals("(")) {
				getToken();
				if (token.getCode() == 4) {
					getToken();
					if (token.getCode() == 0 && token.getValue().equals(",")) {
						getToken();
						if (token.getCode() == 0 && token.getValue().equals("&")) {
							getToken();
							i();
							if (token.getCode() == 0 && token.getValue().equals(")")) {
								getToken();
							} else {
								System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
								throw new Exception("语法错误");
							}
						}
					} else {
						System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
						throw new Exception("语法错误");
					}
				} else {
					System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
					throw new Exception("语法错误");
				}
			} else {
				System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
				throw new Exception("语法错误");
			}
		} else {
			System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
			throw new Exception("语法错误");
		}
	}

}
