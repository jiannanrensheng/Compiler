package Complier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Stack;

//标识符的标识、类型和值
class Node {
	private String value; // 值
	private String type; // 类型：int char bool turn(即为跳转参数，一般为四元式的最后一元辅助参数)

	public Node(String value, String type) {
		this.value = value;
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}

class Quaternary {
	private String caculator; // 运算符
	private String ob1; // 第一个操作数
	private String ob2; // 第二个操作数
	private String ob3; // 辅助参数

	public Quaternary(String caculator, String ob1, String ob2, String ob3) {
		this.caculator = caculator;
		this.ob1 = ob1;
		this.ob2 = ob2;
		this.ob3 = ob3;
	}

	public String getCaculator() {
		return caculator;
	}

	public String getOb1() {
		return ob1;
	}

	public String getOb2() {
		return ob2;
	}

	public String getOb3() {
		return ob3;
	}

	// 设置第3个参数，考虑到可能要回填这个参数
	public void setOb1(String ob1) {
		this.ob1 = ob1;
	}

	public void setOb2(String ob2) {
		this.ob2 = ob2;
	}

	public void setOb3(String ob3) {
		this.ob3 = ob3;
	}

	public String toString() {
		return "(" + caculator + "," + ob1 + "," + ob2 + "," + ob3 + ")";
	}
}

public class SemanticAnalyse {
	ArrayList<Word> wordAnalyseResult;

	private int index;
	private Word token;
	private ArrayList<String> grammarAnalyseResult;

	private String TYPE;
	private LinkedHashMap<String, Node> obList; // 存放标识符的键值对，key为标识符的名称，值为由标识符类型和值组成的Node类
	private static Stack<String> objStack; // 数字栈，用于计算
	private int k; //
	private ArrayList<Quaternary> semanticAnalyseResult; // 存放四元式的列表
	private LinkedList<String> otherCaculator; // 特殊运算符队列(++，--)
	private LinkedList<String> otherObj; // 特殊操作数队列(++,--)

	private LinkedList<String> otherCaculator1; // 特殊运算符队列(++，--),针对if,for和while语句
	private LinkedList<String> otherObj1; // 特殊操作数队列(++,--)

	// 语义分析和中间代码生成结果（四元式）
	public SemanticAnalyse(ArrayList<Word> wordAnalyseResult) {
		// 词法
		this.wordAnalyseResult = wordAnalyseResult;
		this.wordAnalyseResult.add(new Word("#", 0));

		// 语法
		index = -1;
		grammarAnalyseResult = new ArrayList<String>();

		// 语义和中间代码
		obList = new LinkedHashMap<String, Node>();
		k = 0;
		semanticAnalyseResult = new ArrayList<Quaternary>();
		objStack = new Stack<String>();
		otherCaculator = new LinkedList<String>();
		otherObj = new LinkedList<String>();
		otherCaculator1 = new LinkedList<String>();
		otherObj1 = new LinkedList<String>();
	}

	public LinkedHashMap<String, Node> getObList() {
		return obList;
	}

	public ArrayList<Quaternary> getSemanticAnalyseResult() {
		return this.semanticAnalyseResult;
	}

	public HashMap<String, Node> getNodeList() {
		return obList;
	}

	public ArrayList<String> getgrammarAnalyseResult() {
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
		// 每执行完一条定义语句，要检查自增和自减操作
		while (!otherCaculator.isEmpty() && !otherObj.isEmpty()) {
			String obj = otherObj.pop();
			String cal = otherCaculator.pop();

			switch (cal) {
			case "++":
			case "--":
				obList.put("T" + k, new Node("T" + k, "int"));
				semanticAnalyseResult.add(new Quaternary(cal.substring(0, 1), obj, 1 + "", "T" + k));
				semanticAnalyseResult.add(new Quaternary("=", "T" + k, null, obj));
				k++;
				break;
			default:
				break;
			}
		}
	}

	public void B0() throws Exception {
		if (token.getCode() == 0) {
			switch (token.getValue()) {
			case "int":
			case "char":
			case "bool":
				TYPE = token.getValue();
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
			String iName = token.getValue();
			if (obList.get(token.getValue()) != null) {
				throw new Exception("语义错误：重复定义同一标识符类型");
			}
			obList.put(iName, new Node(null, TYPE));
			objStack.push(token.getValue()); // 把赋值也当成一种运算
			i();
			B2();
			String obj1 = objStack.pop();
			if (objStack.isEmpty()) {
				return;
			} else {
				String obj2 = objStack.pop();
				obList.get(obj2).setValue(obList.get(obj1).getValue());
				if (obList.get(obj1).getType().equals(obList.get(obj2).getType())) {
					semanticAnalyseResult.add(new Quaternary("=", obj1, null, obj2));
				} else {
					throw new Exception("语义错误：等式两边类型不一致");
				}
			}

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
			if (obList.get(token.getValue()) == null) {
				throw new Exception("变量" + token.getValue() + "未定义");
			}
			grammarAnalyseResult.add("C -> i C0 ; ");
			String iName = token.getValue();
			objStack.push(iName); // 把赋值也当成一种运算
			i();
			String caculator = token.getValue();
			C0();
			String obj1 = objStack.pop();
			if (!objStack.isEmpty()) {
				String obj2 = objStack.pop();
				obList.get(obj2).setValue(obList.get(obj1).getValue());
				if (obList.get(obj1).getType().equals(obList.get(obj2).getType())) {
					switch (caculator) {
					case "=":
						semanticAnalyseResult.add(new Quaternary("=", obj1, null, obj2));
						break;
					case "+=":
						obList.put("T" + k, new Node("T" + k, "int"));
						semanticAnalyseResult.add(new Quaternary("+", obj2, obj1, "T" + k));
						semanticAnalyseResult.add(new Quaternary("=", "T" + k, null, obj2));
						k++;
						break;
					case "-=":
						obList.put("T" + k, new Node("T" + k, "int"));
						semanticAnalyseResult.add(new Quaternary("-", obj2, obj1, "T" + k));
						semanticAnalyseResult.add(new Quaternary("=", "T" + k, null, obj2));
						k++;
						break;
					case "*=":
						obList.put("T" + k, new Node("T" + k, "int"));
						semanticAnalyseResult.add(new Quaternary("*", obj2, obj1, "T" + k));
						semanticAnalyseResult.add(new Quaternary("=", "T" + k, null, obj2));
						k++;
						break;
					case "/=":
						obList.put("T" + k, new Node("T" + k, "int"));
						semanticAnalyseResult.add(new Quaternary("/", obj2, obj1, "T" + k));
						semanticAnalyseResult.add(new Quaternary("=", "T" + k, null, obj2));
						k++;
						break;
					case "%=":
						obList.put("T" + k, new Node("T" + k, "int"));
						semanticAnalyseResult.add(new Quaternary("%", obj2, obj1, "T" + k));
						semanticAnalyseResult.add(new Quaternary("=", "T" + k, null, obj2));
						k++;
						break;
					case "++":
					case "--":
					default:
						break;
					}
				} else {
					throw new Exception("语义错误：运算符两边类型不一致");
				}
			} else {
				obList.put("T" + k, new Node("T" + k, "int"));
				semanticAnalyseResult.add(new Quaternary(caculator.substring(0, 1), obj1, 1 + "", "T" + k));
				semanticAnalyseResult.add(new Quaternary("=", "T" + k, null, obj1));
				k++;
			}

			if (token.getValue().equals(";")) {
				getToken();
				// 每执行完一条赋值语句后,检查以下自增和自减语句
				while (!otherCaculator.isEmpty() && !otherObj.isEmpty()) {
					String obj = otherObj.pop();
					String cal = otherCaculator.pop();

					switch (cal) {
					case "++":
					case "--":
						semanticAnalyseResult.add(new Quaternary(cal.substring(0, 1), obj, 1 + "", "T" + k));
						semanticAnalyseResult.add(new Quaternary("=", "T" + k, null, obj));
						k++;
						break;
					default:
						break;
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
		String caculator = null;
		grammarAnalyseResult.add("N -> N1 N0");
		N1();
		switch (token.getValue()) {
		case "||":
			caculator = "||";
			break;
		default:
			break;
		}
		N0();
		if (caculator != null) {
			String obj1 = objStack.pop();
			String obj2 = objStack.pop();
			String type1 = obList.get(obj1).getType();
			String type2 = obList.get(obj1).getType();
			if (!type1.equals(type2)) {
				throw new Exception("语义错误：运算符两边的类型不相同");
			}
			if (!type1.equals("bool")) {
				throw new Exception("非布尔型，无法运算");
			}
			obList.put("T" + k, new Node("T" + k, "bool"));
			objStack.push("T" + k);
			semanticAnalyseResult.add(new Quaternary(caculator, obj2, obj1, "T" + k));
			k++;
		}
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
		String caculator = null;
		grammarAnalyseResult.add("N1 -> N3 N2");
		N3();
		switch (token.getValue()) {
		case "&&":
			caculator = "&&";
			break;
		default:
			break;
		}
		N2();
		if (caculator != null) {
			String obj1 = objStack.pop();
			String obj2 = objStack.pop();
			String type1 = obList.get(obj1).getType();
			String type2 = obList.get(obj1).getType();
			if (!type1.equals(type2)) {
				throw new Exception("语义错误：运算符两边的类型不相同");
			}
			if (!type1.equals("bool")) {
				throw new Exception("语义错误：非布尔型，无法运算");
			}
			obList.put("T" + k, new Node("T" + k, "bool"));
			objStack.push("T" + k);
			semanticAnalyseResult.add(new Quaternary(caculator, obj2, obj1, "T" + k));
			k++;
		}
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
		String caculator = null;
		grammarAnalyseResult.add("N3 -> N5 N4");
		N5();
		switch (token.getValue()) {
		case "==":
			caculator = "==";
			break;
		case "!=":
			caculator = "!=";
			break;
		default:
			break;
		}
		N4();
		if (caculator != null) {
			String obj1 = objStack.pop();
			String obj2 = objStack.pop();
			String type1 = obList.get(obj1).getType();
			String type2 = obList.get(obj1).getType();
			if (!type1.equals(type2)) {
				throw new Exception("语义错误：运算符两边的类型不相同");
			}
			obList.put("T" + k, new Node("T" + k, "bool"));
			objStack.push("T" + k);
			semanticAnalyseResult.add(new Quaternary(caculator, obj2, obj1, "T" + k));
			k++;
		}

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

	// >= <= > <
	public void N5() throws Exception {
		String caculator = null;
		grammarAnalyseResult.add("N5 -> N7 N6");
		N7();
		switch (token.getValue()) {
		case ">=":
			caculator = ">=";
			break;
		case "<=":
			caculator = "<=";
			break;
		case ">":
			caculator = ">";
			break;
		case "<":
			caculator = "<";
			break;
		default:
			break;
		}

		N6();
		if (caculator != null) {
			String obj1 = objStack.pop();
			String obj2 = objStack.pop();
			String type1 = obList.get(obj1).getType();
			String type2 = obList.get(obj2).getType();
			if (!type1.equals(type2)) {
				throw new Exception("语义错误：运算符两边的类型不相同");
			}

			obList.put("T" + k, new Node("T" + k, "bool"));
			objStack.push("T" + k);
			semanticAnalyseResult.add(new Quaternary(caculator, obj2, obj1, "T" + k));
			k++;
		}

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
		String caculator = null;
		grammarAnalyseResult.add("N7 -> N9 N8");
		N9();
		switch (token.getValue()) {
		case "+":
			caculator = "+";
			break;
		case "-":
			caculator = "-";
			break;
		default:
			break;
		}
		N8();
		if (caculator != null) {
			String obj1 = objStack.pop();
			String obj2 = objStack.pop();
			String type1 = obList.get(obj1).getType();
			String type2 = obList.get(obj2).getType();
			if (!type1.equals(type2)) {
				throw new Exception("语义错误：运算符两边的类型不相同");
			}
			if (type1.equals("char") || type1.equals("bool")) {
				throw new Exception("语义错误：非法运算");
			}
			obList.put("T" + k, new Node("T" + k, "int"));
			objStack.push("T" + k);
			semanticAnalyseResult.add(new Quaternary(caculator, obj2, obj1, "T" + k));
			k++;
		}
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
		String caculator = null;
		grammarAnalyseResult.add("N9 -> N11 N10");
		N11();
		// 如果
		switch (token.getValue()) {
		case "*":
			caculator = "*";
			break;
		case "/":
			caculator = "/";
			break;
		case "%":
			caculator = "%";
			break;
		default:
			break;
		}
		N10();
		if (caculator != null) {
			String obj1 = objStack.pop();
			String obj2 = objStack.pop();
			String type1 = obList.get(obj1).getType();
			String type2 = obList.get(obj2).getType();
			// 首先检查两边类型是否相同
			if (!type1.equals(type2)) {
				throw new Exception("语义错误：运算符两边的类型不相同");
			}
			if (type1.equals("char") || type1.equals("bool")) {
				throw new Exception("语义错误：非法运算");
			}

			obList.put("T" + k, new Node("T" + k, "int"));
			objStack.push("T" + k);
			semanticAnalyseResult.add(new Quaternary(caculator, obj2, obj1, "T" + k));
			k++;
		}
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
				grammarAnalyseResult.add("N10 -> $");
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
				obList.put(token.getValue(), new Node(token.getValue(), "bool"));
				obList.put("T" + k, new Node("T" + k, "bool"));
				semanticAnalyseResult.add(new Quaternary("=", token.getValue(), null, "T" + k));
				objStack.push("T" + k);
				k++;
				b();

				break;
			default:
				System.out.println(grammarAnalyseResult.get(grammarAnalyseResult.size() - 1));
				throw new Exception("语法错误");
			}
		} else if (token.getCode() == 1) {
			objStack.push(token.getValue());
			String iName = token.getValue(); // 记录标识符的名称，如果表达式有自增或者自减操作需要用到
			if (obList.get(iName) == null) {
				throw new Exception("标识符" + iName + "未定义");
			}
			if (obList.get(iName).getValue() == null) {
				throw new Exception("标识符" + iName + "未初始化赋值");
			}

			i();
			if (token.getValue().equals("++") || token.getValue().equals("--")) {
				if (!obList.get(iName).getType().equals("int")) {
					throw new Exception("语义错误：只有int类型数据可以做自增或自减操作");
				}
				otherObj.push(iName);
				otherCaculator.push(token.getValue());
			}
			N12();
		} else if (token.getCode() == 2) {
			obList.put(token.getValue(), new Node(token.getValue(), "int"));
			obList.put("T" + k, new Node("T" + k, "int"));
			semanticAnalyseResult.add(new Quaternary("=", token.getValue(), null, "T" + k));
			objStack.push("T" + k);
			k++;
			d();

		} else if (token.getCode() == 3) {
			obList.put("'" + token.getValue() + "'", new Node("'" + token.getValue() + "'", "char"));
			obList.put("T" + k, new Node("T" + k, "char"));
			semanticAnalyseResult.add(new Quaternary("=", "'" + token.getValue() + "'", null, "T" + k));
			objStack.push("T" + k);
			k++;
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
					String obj1 = objStack.pop();
					int fjIndex = semanticAnalyseResult.size();
					semanticAnalyseResult.add(new Quaternary("FJ", null, obj1, null));

					// 每执行完一条条件语句后需要注意自增和自减操作
					// 如果不为空,把它们存进另外一个队列中，针对于if,while和for
					int size = otherCaculator.size(); // 记录if,for,while语句中++和--的运算条数
					while (!otherCaculator.isEmpty() && !otherObj.isEmpty()) {
						String obj = otherObj.pop();
						String cal = otherCaculator.pop();
						otherObj1.push(obj);
						otherCaculator1.push(cal);
					}
					A();
					if (token.getCode() == 0 && token.getValue().equals("else")) {
						semanticAnalyseResult.get(fjIndex).setOb1((semanticAnalyseResult.size() + 1) + "");
					} else {
						semanticAnalyseResult.get(fjIndex).setOb1((semanticAnalyseResult.size()) + "");
					}
					semanticAnalyseResult.add(new Quaternary("RJ", null, null, null));
					int rjIndex = semanticAnalyseResult.size() - 1;
					D0();
					semanticAnalyseResult.get(rjIndex).setOb1(semanticAnalyseResult.size() - 1 + "");

					while (!otherCaculator1.isEmpty() && !otherObj1.isEmpty() && (size--) > 0) {
						String obj = otherObj1.pop();
						String cal = otherCaculator1.pop();

						switch (cal) {
						case "++":
						case "--":
							obList.put("T" + k, new Node("T" + k, "int"));
							semanticAnalyseResult.add(new Quaternary(cal.substring(0, 1), obj, 1 + "", "T" + k));
							semanticAnalyseResult.add(new Quaternary("=", "T" + k, null, obj));
							k++;
							break;
						default:
							break;
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
	}

	public void D0() throws Exception {
		if (token.getCode() == 1) {
			grammarAnalyseResult.add("D0 -> $");
		} else if (token.getCode() == 0) {
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
				int rjIndex = semanticAnalyseResult.size();
				N();
				if (token.getCode() == 0 && token.getValue().equals(")")) {
					String obj2 = objStack.pop();
					// 得到布尔值后就直接跳到它的下一句，无论是否后面内容为空，因为后面必有一个RJ与之对应
					int fjIndex = semanticAnalyseResult.size();
					semanticAnalyseResult.add(new Quaternary("FJ", null, obj2, null));
					getToken();

					int size = otherCaculator.size(); // 记录if,for,while语句中++和--的运算条数
					while (!otherCaculator.isEmpty() && !otherObj.isEmpty()) {
						String obj = otherObj.pop();
						String cal = otherCaculator.pop();
						otherObj1.push(obj);
						otherCaculator1.push(cal);
					}
					A();
					semanticAnalyseResult.add(new Quaternary("RJ", rjIndex + "", null, null));
					semanticAnalyseResult.get(fjIndex).setOb1(semanticAnalyseResult.size() + "");

					while (!otherCaculator1.isEmpty() && !otherObj1.isEmpty() && (size--) > 0) {
						String obj = otherObj1.pop();
						String cal = otherCaculator1.pop();

						switch (cal) {
						case "++":
						case "--":
							semanticAnalyseResult.add(new Quaternary(cal.substring(0, 1), obj, 1 + "", "T" + k));
							semanticAnalyseResult.add(new Quaternary("=", "T" + k, null, obj));
							k++;
							break;
						default:
							break;
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
	}

	// for语句
	public void F() throws Exception {
		if (token.getCode() == 0 && token.getValue().equals("for")) {
			grammarAnalyseResult.add("F -> for ( C ; N ; i C0 ) A");
			getToken();
			if (token.getCode() == 0 && token.getValue().equals("(")) {
				getToken();
				C();
				int rjIndex = semanticAnalyseResult.size();
				N();
				String ob = objStack.pop();
				int fjIndex = semanticAnalyseResult.size();
				semanticAnalyseResult.add(new Quaternary("FJ", null, ob, null));

				// 判断第三个式子
				if (token.getCode() == 0 && token.getValue().equals(";")) {
					getToken();
					if (token.getCode() == 1) {
						if (obList.get(token.getValue()) == null) {
							throw new Exception("语义错误:变量" + token.getValue() + "未定义");
						}
						String iName = token.getValue();
						objStack.push(iName); // 把赋值也当成一种运算
						i();
						String caculator = token.getValue();
						C0();
						String obj1 = objStack.pop();
						String obj2 = null;
						if (!objStack.isEmpty()) {
							obj2 = objStack.pop();
							if (!obList.get(obj1).getType().equals(obList.get(obj2).getType())) {
								throw new Exception("语义错误：赋值符号两边的类型不同");
							}
						}
						if (token.getValue().equals(")")) {
							getToken();
							int size = otherCaculator.size(); // 记录if,for,while语句中++和--的运算条数
							while (!otherCaculator.isEmpty() && !otherObj.isEmpty()) {
								String obj = otherObj.pop();
								String cal = otherCaculator.pop();
								otherObj1.push(obj);
								otherCaculator1.push(cal);
							}
							A();
							while (!otherCaculator1.isEmpty() && !otherObj1.isEmpty() && (size--) > 0) {
								String obj = otherObj1.pop();
								String cal = otherCaculator1.pop();

								switch (cal) {
								case "++":
								case "--":
									obList.put("T" + k, new Node("T" + k, "int"));
									semanticAnalyseResult
											.add(new Quaternary(cal.substring(0, 1), obj, 1 + "", "T" + k));
									semanticAnalyseResult.add(new Quaternary("=", "T" + k, null, obj));
									k++;
									break;
								default:
									break;
								}
							}
							switch (caculator) {
							case "=":
								obList.put("T" + k, new Node("T" + k, "int"));
								semanticAnalyseResult.add(new Quaternary(caculator, obj2, obj1, "T" + k));
								k++;
								break;
							case "+=":
							case "-=":
							case "*=":
							case "/=":
							case "%=":
								obList.put("T" + k, new Node("T" + k, "int"));
								semanticAnalyseResult
										.add(new Quaternary(caculator.substring(0, 1), obj2, obj1, "T" + k));
								semanticAnalyseResult.add(new Quaternary("=", "T" + k, null, obj2));
								k++;
								break;
							case "++":
							case "--":
								obList.put("T" + k, new Node("T" + k, "int"));
								semanticAnalyseResult
										.add(new Quaternary(caculator.substring(0, 1), obj1, 1 + "", "T" + k));
								semanticAnalyseResult.add(new Quaternary("=", "T" + k, null, obj1));
								k++;
							default:
								break;
							}
							semanticAnalyseResult.add(new Quaternary("RJ", rjIndex + "", null, null));
							semanticAnalyseResult.get(fjIndex).setOb1(semanticAnalyseResult.size() + "");

						} else {
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

	// printf语句
	public void G1() throws Exception {
		if (token.getCode() == 0 && token.getValue().equals("printf")) {
			grammarAnalyseResult.add("G1 -> printf ( s , i ) ;");
			getToken();
			if (token.getCode() == 0 && token.getValue().equals("(")) {

				getToken();
				String ob = token.getValue(); // 占位符(%d,%c)
				if (token.getCode() == 4) {
					getToken();
					if (token.getCode() == 0 && token.getValue().equals(",")) {
						getToken();
						String outObj = token.getValue();
						String type = obList.get(outObj).getType();
						if (ob.equals("%d") && type.equals("int") || ob.equals("%c") && type.equals("char")) {
							semanticAnalyseResult.add(new Quaternary("printf", outObj, null, null));
						} else {
							throw new Exception("语义错误：占位符与标识符不匹配");
						}
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
				String ob = token.getValue();
				if (token.getCode() == 4) {
					getToken();
					if (token.getCode() == 0 && token.getValue().equals(",")) {
						getToken();
						if (token.getCode() == 0 && token.getValue().equals("&")) {
							getToken();
							String outObj = token.getValue();
							String type = obList.get(outObj).getType();
							if (ob.equals("%d") && type.equals("int") || ob.equals("%c") && type.equals("char")) {
								semanticAnalyseResult.add(new Quaternary("scanf", outObj, null, null));
							} else {
								throw new Exception("语义错误：占位符与标识符不匹配");
							}
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
