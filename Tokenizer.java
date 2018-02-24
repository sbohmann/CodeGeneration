	public List<Token<?>> run()
	throws IOException
	{
		try (Reader reader = IoTools.createReader(file))
		{
			List<Token<?>> tokens = new ArrayList<>();
			
			int line = 1;
			int col = 1;
			
			TokenBuffer tokenBuffer = new TokenBuffer(tokens);
			
			boolean insideComment = false;
			
			boolean insideStringLiteral = false;
			boolean insideNumberLiteral = false;
			boolean escaped = false;
			
			while (true)
			{
				int n = reader.read();
				if (n < 0)
				{
					break;
				}
				
				char c = (char)n;
				
				if (insideComment)
				{
					if (c == '\n')
					{
						insideComment = false;
						++line;
						col = 1;
					}
					continue;
				}
				
				switch(c)
				{
				case '\r':
					if (insideStringLiteral)
					{
						parsingError(file,line,col,"Found \\r inside String Literal");
					}
					tokenBuffer.finishToken();
					insideNumberLiteral = false;
					continue;
				case '\n':
					if (insideStringLiteral)
					{
						parsingError(file,line,col,"Found \\n inside String Literal");
					}
					tokenBuffer.finishToken();
					insideNumberLiteral = false;
					++line;
					col = 1;
					continue;
				case '#':
					if (insideStringLiteral)
					{
						tokenBuffer.append(line,col,c);
					}
					else
					{
						tokenBuffer.finishToken();
						insideNumberLiteral = false;
						insideComment = true;
					}
					++col;
					continue;
				case ' ':
					if (insideStringLiteral)
					{
						tokenBuffer.append(line,col,c);
					}
					else
					{
						tokenBuffer.finishToken();
						insideNumberLiteral = false;
					}
					++col;
					continue;
				case '\t':
					if (insideStringLiteral)
					{
						parsingError(file,line,col,"Found \\t inside String Literal");
					}
					tokenBuffer.finishToken();
					insideNumberLiteral = false;
					col = (col - 1 + colsForTab) / colsForTab * colsForTab + 1;
					continue;
				case '(':
				case ')':
				case '[':
				case ']':
				case '{':
				case '}':
					if (insideStringLiteral)
					{
						tokenBuffer.append(line,col,c);
					}
					else
					{
						tokenBuffer.finishToken();
						insideNumberLiteral = false;
						tokens.add(new BracketToken(file,line,col,Character.toString(c)));
					} 
					++col;
					continue;
				case ';':
					if (insideStringLiteral)
					{
						tokenBuffer.append(line,col,c);
					}
					else
					{
						tokenBuffer.finishToken();
						insideNumberLiteral = false;
						tokens.add(new SemicolonToken(file,line,col,Character.toString(c)));
					} 
					++col;
					continue;
				case '.':
					if (insideStringLiteral || insideNumberLiteral)
					{
						tokenBuffer.append(line,col,c);
					}
					else
					{
						tokenBuffer.finishToken();
						tokens.add(new DotToken(file,line,col,Character.toString(c)));
					} 
					++col;
					continue;
				case ',':
					if (insideStringLiteral)
					{
						tokenBuffer.append(line,col,c);
					}
					else
					{
						tokenBuffer.finishToken();
						insideNumberLiteral = false;
						tokens.add(new CommaToken(file,line,col,Character.toString(c)));
					} 
					++col;
					continue;
				case ':':
					if (insideStringLiteral)
					{
						tokenBuffer.append(line,col,c);
					}
					else
					{
						tokenBuffer.finishToken();
						insideNumberLiteral = false;
						tokens.add(new ColonToken(file,line,col,Character.toString(c)));
					} 
					++col;
					continue;
				case '"':
					if (insideStringLiteral)
					{
						tokenBuffer.append(line, col, c);
						if (escaped == false)
						{
							insideStringLiteral = false;
						}
						escaped = false;
					}
					else
					{
						tokenBuffer.append(line, col, c);
						insideStringLiteral = true;
						escaped = false;
					}
					++col;
					continue;
				case '\\':
					if (insideStringLiteral)
					{
						escaped = !escaped;
						tokenBuffer.append(line, col, c);
					}
					else
					{
						parsingError(file,line,col,"Illegal character: \\t");
					}
					++col;
					continue;
				default:
					if (tokenBuffer.beforeToken() && Character.isDigit(c))
					{
						insideNumberLiteral = true;
					}
					tokenBuffer.append(line,col,c);
					++col;
					continue;
				}
			}
			
			return tokens;
		}
	}
