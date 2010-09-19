package client.protocol.imap;

import java.util.LinkedList;
import java.util.Stack;

import client.protocol.imap.bean.ConCell;
import client.protocol.imap.bean.IVisitor;

public class ListReader {
	// TODO: a very important code, need full test 
	public static  ConCell parse(String text, IVisitor visitor) {
		Stack<Character> rs = new Stack<Character>(); // record stack
		StringBuilder sb    = new StringBuilder();
		ConCell head        = null;
		ConCell context     = null;
		ConCell cell        = null;

		for(int i=0; i<text.length(); ++i) {
			char ch = text.charAt(i);
			switch(ch) {
			case '"': 
				if(!rs.isEmpty() && rs.peek() == '"') {
					rs.pop();
				} else {
					rs.push(ch);
				}
				break;
			case '(':
				if(!rs.isEmpty() && rs.peek() == '"') {
					sb.append(ch);
				} else {
					rs.push(ch); 
					
					// create new concel
					cell = new ConCell();
					cell.setList(new LinkedList<ConCell>());
					cell.setParent(context);
					
					// switch context
					if(head == null) {
						head = cell;
					} else {
						context.getList().add(cell);
					}
					context = cell;
					if(visitor != null) {
						visitor.onNewConCell(cell);
					}
				}				
				break;
			case ')':
				if(!rs.isEmpty() && rs.peek() == '"') {
					sb.append(ch);
				} else {
					// TODO: if top is null ,  error 
					rs.pop();
					
					if(sb.toString().length() > 0) {
						ConCell newCell = new ConCell(sb.toString());
						if(visitor != null) {
							visitor.onNewConCell(newCell);
						}
						context.getList().add(newCell);
						sb.delete(0, sb.length());
					}
					
					context = context.getParent();
					// over
					if(context == null) {
						//System.err.println("over");
						return head;
					}
				}
				break;
			case ' ':
				if(!rs.isEmpty() && rs.peek() == '"') {
					sb.append(ch);
				} else if(rs.size()>0 && rs.peek() == '(' && sb.toString().length() == 0) {
					/* do noting */
				} else {
					///System.err.println(">>>" + sb.toString());
					ConCell newCell = new ConCell(sb.toString());
					if(visitor != null) {
						visitor.onNewConCell(newCell);
					}
					context.getList().add(newCell);
					// clear 
					sb.delete(0, sb.length());
				}
				break;
			default:
				sb.append(ch);
				break;
			}
		}
		//TODO: 
		return null;
	}
}
