package c3.assets;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import c3.core.*;
import c3.exceptions.*;
import c3.math.*;

class Mesh {
	
	private String name;
	private ArrayList<Socket> sockets = new ArrayList<Socket>();
	private ArrayList<TriangleBatch> triangleBatches = new ArrayList<TriangleBatch>();
	
	public Mesh(String name) {
		this.name = name;
	}
	
	public ArrayList<TriangleBatch> getTriangleBatches() {
		return triangleBatches;
	}
	
	public String getName() {
		return name;
	}
	
	public void addSocket(Socket socket) {
		sockets.add(socket);
	}
	
	public void addTriangleBatch(TriangleBatch trianglebatch) {
		triangleBatches.add(trianglebatch);
	}
	
}

class TriangleBatch {
	
	ArrayList<Triangle> triangles = new ArrayList<Triangle>();
	
	private String program;
	
	public TriangleBatch(String program) {
		this.program = program;
	}
	
	public String getProgram() {
		return program;
	}
	
	public ArrayList<Triangle> getTriangle() {
		return triangles;
	}
	
	public void addTriangle(Triangle triangle) {
		triangles.add(triangle);
	}
	
}

class Triangle {
	
	private Vertex v1, v2, v3;
	
	public Triangle(Vertex v1, Vertex v2, Vertex v3) {
		this.v1 = v1;
		this.v2 = v2;
		this.v3 = v3;
	}
	
	public Vertex getVertex1() {
		return v1;
	}
	
	public Vertex getVertex2() {
		return v2;
	}
	
	public Vertex getVertex3() {
		return v3;
	}
	
}

class Vertex {
	
	private Vector3f position;
	private Vector3f normal;
	private Vector3f texcoord;
	
	public Vertex() {
		
	}
	
	public Vertex(Vector3f position, Vector3f normal, Vector3f texcoord) {
		this.position = position;
		this.normal = normal;
		this.texcoord = texcoord;
	}
	
	public Vertex(Vector3f position, Vector3f normal) {
		this.position = position;
		this.normal = normal;
	}
	
	public Vertex(Vector3f position) {
		this.position = position;
	}
	
	public Vector3f getPosition() {
		return position;
	}
	
	public Vector3f getNormal() {
		return normal;
	}
	
	public Vector3f getTextureCoord() {
		return texcoord;
	}
	
	public void setPosition(Vector3f position) {
		this.position = position;
	}
	
	public void setNormal(Vector3f normal) {
		this.normal = normal;
	}
	
	public void setTextureCoord(Vector3f texcoord) {
		this.texcoord = texcoord;
	}
	
}

class Socket {
	
}

public class ModelAsset extends Asset {
	
	private ArrayList<Mesh> meshes;
	private ArrayList<Vector3f> vertices;
	private ArrayList<Vector3f> normals;
	private ArrayList<Vector3f> texcoords;

	public static void indexAllAssets() {
		
		Console.log("Indexing all Model assets...");
		
		File[] files = Utility.getFilesWithExtension("./models/", ".pmodel");
		
		if(files == null) {
			return;
		}
		
		for(File file : files) {
			
			FString name = new FString(file.getName());
			
			if(Asset.exists(name)) {
				continue;
			}
			
			new VertexShaderAsset(name, new FString(file.getPath()));
			
		}
				
	}
	
	public ModelAsset(FString name, FString path) {
		
		super(name, path);
		
		Console.log("Found new model asset: " + name);
		
	}

	@Override
	public void load() throws AssetLoadException {
		
		// Load the model from file
		try {
			assetData = Utility.getFileAsString(path);
		} catch (IOException e) {
			throw new AssetLoadException(this, "Failed to load asset from file: " + path);
		}
		
		// Begin OBJ parsing...
		
		meshes = new ArrayList<Mesh>();
		vertices = new ArrayList<Vector3f>();
		normals = new ArrayList<Vector3f>();
		texcoords = new ArrayList<Vector3f>();
		
		final long startTime = System.currentTimeMillis();
		
		TriangleBatch currentTriangleBatch = null;
		
		Mesh currentMesh = null;
		
		Path file = Paths.get(path.getString());

		List<String> lines = null;
		
	    try {
	    	
	      lines = Files.readAllLines(file, Charset.forName("ISO-8859-1"));

	    } catch (IOException e) {
	    	
	      System.out.println(e);
	      
	      return;
	      
	    }

		// Process each line
	    if(lines != null) {
	    	
		    	for(String line : lines) {
		    		
		    		// Check for empty lines or comments
		    		if(line.length() == 0 || line.equals("") || line.charAt(0) == '#') {
		    			
		    			// Do nothing
		    			continue;
		    			
		    		}
		    		
		    		// Break the line into symbols
		    		String[] symbols = line.split("\\s+");
		    		
		    		// Check for vertex lines
		    		if(symbols[0].equals("v") && symbols.length == 4) {
		    			
		    			try {
		    				float x = Float.parseFloat(symbols[1]);
		    				float y = Float.parseFloat(symbols[2]);
		    				float z = Float.parseFloat(symbols[3]);
		    				vertices.add(new Vector3f(x, y, z));
		    			}
		    			
		    			catch(NumberFormatException | NullPointerException e) {
		    				
		    			    System.out.println("Found invalid vertex, model import failed.");
		    			    
		    			    return;
		    			    
		    			}
		    			
		    			continue;
		    			
		    		}
		    		
		    		// Check for vertex normal lines
		    		if(symbols[0].equals("vn") && symbols.length == 4) {
		    			
		    			try {
		    				float x = Float.parseFloat(symbols[1]);
		    				float y = Float.parseFloat(symbols[2]);
		    				float z = Float.parseFloat(symbols[3]);
		    				normals.add(new Vector3f(x, y, z));
		    			}
		    			catch(NumberFormatException | NullPointerException e) {
		    				
		    			    System.out.println("Found invalid vertex normal, model import failed.");
		    			    
		    			    return;
		    			    
		    			}
		    			
		    			continue;
		    			
		    		}
		    		
		    		// Check for vertex texture lines
		    		if(symbols[0].equals("vt") && symbols.length == 4) {
		    			
		    			try {
		    				float x = Float.parseFloat(symbols[1]);
		    				float y = Float.parseFloat(symbols[2]);
		    				float z = Float.parseFloat(symbols[3]);
		    				texcoords.add(new Vector3f(x, y, z));
		    			}
		    			catch(NumberFormatException | NullPointerException e) {
		    				
		    			    System.out.println("Found invalid vertex normal, model import failed.");
		    			    
		    			    return;
		    			    
		    			}
		    			
		    			continue;
		    			
		    		}
		    		
		    		// Check for geometry lines
		    		if(symbols[0].equals("g")) {
		    			
		    			// New mesh detected, create a new mesh and set it to current mesh
		    			if(line.length() > 2) {
		    				currentMesh = new Mesh(line.substring(2));
		    			} else {
		    				currentMesh = new Mesh("Null Mesh");
		    			}
		    			
		    			// Add the mesh to the mesh list
		    			meshes.add(currentMesh);
		    			
		    			// Set the current triangle batch to null
		    			currentTriangleBatch = null;
		    			
		    			continue;
		    			
		    		}
		    		
		    		// Ignore smoothing group 's' ...
		    		
		    		// Check for material lines
		    		if(symbols[0].equals("usemtl")) {
		    			
		    			// Create a new TriangleBtach, set it to current TriangleBatch
		    			if(line.length() > 7) {
		    				currentTriangleBatch = new TriangleBatch(line.substring(7));
		    			} else {
		    				currentTriangleBatch = new TriangleBatch("Null Batch");
		    			}
		    			
		    			// Add it to the current meshes TriangleBatch list
		    			currentMesh.addTriangleBatch(currentTriangleBatch);
		    			
		    			continue;
		    			
		    		}
		    		
		    		// Check for (tri) face lines
		    		if(symbols[0].equals("f") && symbols.length == 4) {
		    			
		    			Vertex v1 = null;
		    			Vertex v2 = null;
		    			Vertex v3 = null;
		    			
		    			if(symbols[1].split("/").length == 3) {
		    				
		    				// Each face vertex contains a vertex, normal and texture coordinate
			    			v1 = parseVTN(symbols[1], vertices, normals, texcoords);
			    			v2 = parseVTN(symbols[2], vertices, normals, texcoords);
			    			v3 = parseVTN(symbols[3], vertices, normals, texcoords);
			    			
		    			} else if(symbols[1].split("//").length == 2) {
		    				
		    				// Each face vertex contains a vertex and normal
			    			v1 = parseVN(symbols[1], vertices, normals);
			    			v2 = parseVN(symbols[2], vertices, normals);
			    			v3 = parseVN(symbols[3], vertices, normals);
			    		
		    			} else {
		    				
		    				// Each face vertex contains only a vertex
			    			v1 = parseV(symbols[1], vertices);
			    			v2 = parseV(symbols[2], vertices);
			    			v3 = parseV(symbols[3], vertices);
		    				
		    			}
		    			
		    			// Make sure we have a triangle batch
		    			if(currentTriangleBatch == null) {
		    				
		    				currentTriangleBatch = new TriangleBatch("Null Batch");
		    				currentMesh.addTriangleBatch(currentTriangleBatch);
		    				
		    			}

		    			// Assemble the triangle and add it to the current batch
		    			currentTriangleBatch.addTriangle(new Triangle(v1, v2, v3));
		    			
		    			continue;
		    			
		    		}
		    		
		    		// Check for (quad) face lines
		    		if(symbols[0].equals("f") && symbols.length == 5) {
		    			
		    			Vertex v1 = null;
		    			Vertex v2 = null;
		    			Vertex v3 = null;
		    			Vertex v4 = null;
		    			
		    			if(symbols[1].split("/").length == 3) {
		    				
		    				// Each face vertex contains a vertex, normal and texture coordinate
			    			v1 = parseVTN(symbols[1], vertices, normals, texcoords);
			    			v2 = parseVTN(symbols[2], vertices, normals, texcoords);
			    			v3 = parseVTN(symbols[3], vertices, normals, texcoords);
			    			v4 = parseVTN(symbols[4], vertices, normals, texcoords);
			    			
		    			} else if(symbols[1].split("//").length == 2) {
		    				
		    				// Each face vertex contains a vertex and normal
			    			v1 = parseVN(symbols[1], vertices, normals);
			    			v2 = parseVN(symbols[2], vertices, normals);
			    			v3 = parseVN(symbols[3], vertices, normals);
			    			v4 = parseVN(symbols[4], vertices, normals);
			    			
		    			} else {
		    				
		    				// Each face vertex contains only a vertex
			    			v1 = parseV(symbols[1], vertices);
			    			v2 = parseV(symbols[2], vertices);
			    			v3 = parseV(symbols[3], vertices);
			    			v4 = parseV(symbols[4], vertices);
		    				
		    			}
		    			
		    			// Make sure we have a triangle batch
		    			if(currentTriangleBatch == null) {
		    				
		    				currentTriangleBatch = new TriangleBatch("Null");
		    				currentMesh.addTriangleBatch(currentTriangleBatch);
		    				
		    			}
		    			
		    			// Assemble the triangles and add it to the current batch
		    			currentTriangleBatch.addTriangle(new Triangle(v1, v2, v3));
		    			currentTriangleBatch.addTriangle(new Triangle(v1, v3, v4));
		    			
		    			continue;
		    			
		    		}
		    		
		    		// Check for (n-gon) face lines
		    		if(symbols[0].equals("f") && symbols.length > 5) {
		    			
		    			ArrayList<Vertex> faceVertices = new ArrayList<Vertex>();
		    			
		    			String[] faceblocks = line.substring(2).split("\\s+");
		    			
		    			for(String faceblock : faceblocks) {
		    				
		    				if(symbols[1].split("/").length == 3) {
		    				
		    					// Each face vertex contains a vertex, normal and texture coordinate
		    					faceVertices.add(parseVTN(faceblock, vertices, normals, texcoords));
		    					
		    				} else if(symbols[1].split("//").length == 2) {
		    					
		    					// Each face vertex contains a vertex and normal
		    					faceVertices.add(parseVN(faceblock, vertices, normals));
		    					
		    				} else {
		    					
		    					// Each face vertex contains only a vertex
		    					faceVertices.add(parseV(faceblock, vertices));
		    					
		    				}
		    				
		    			}
		    			
		    			for(int i = 1; i < faceVertices.size() - 2; ++i) {
	    					
	    					// Assemble the triangles and add it to the current batch
	    					currentTriangleBatch.addTriangle(new Triangle(
	    						faceVertices.get(0),
	    						faceVertices.get(i),
	    						faceVertices.get(i + 1)));
	    					
	    				}
		    			
		    			continue;
		    			
		    		}
		    		

		    }
		    	
		    	final long endTime = System.currentTimeMillis();
		    	final long time  = (endTime - startTime);
		    	
		    	System.out.println("Conversion took: " + time + " ms");
		    	System.out.println("Found " + meshes.size() + " m, " + vertices.size() + " v, " + normals.size() + " n, " + texcoords.size() + " tc");
		    	
		    	for(Mesh mesh : meshes) {
		    		
		    		System.out.println(mesh.getName());
		    		
		    		for(TriangleBatch batch : mesh.getTriangleBatches()) {
		    			System.out.println("- " + batch.getProgram() + " (" + batch.getTriangle().size() + ")");
		    		}
		    		
		    	}
		    	
	    }
		
		return;
		
	}

	@Override
	public void unload() {
		
		// TODO specify data format, then unload
		
	}
	
	private Vertex parseVTN(String vertSymbol,
			ArrayList<Vector3f> vertices,
			ArrayList<Vector3f> normals,
			ArrayList<Vector3f> texcoords) {
		
		try {
			
			String[] subsymbols = vertSymbol.split("/");
			
			int v = Integer.parseInt(subsymbols[0]);
			int vt = Integer.parseInt(subsymbols[1]);
			int vn = Integer.parseInt(subsymbols[2]);
			
			Vector3f position = vertices.get(v - 1);
			Vector3f normal = normals.get(vn - 1);
			Vector3f texcoord = texcoords.get(vt - 1);
			
			return new Vertex(position, normal, texcoord);
			
		} catch(NumberFormatException | NullPointerException e) {
			
		    System.out.println("Found invalid vertex normal, model import failed.");
		    
		    return null;
		    
		}
		
	}
	
	private Vertex parseVN(String vertSymbol,
			ArrayList<Vector3f> vertices,
			ArrayList<Vector3f> normals) {
		
		try {
			
			String[] subsymbols = vertSymbol.split("//");
			
			int v = Integer.parseInt(subsymbols[0]);
			int vn = Integer.parseInt(subsymbols[1]);
			
			Vector3f position = vertices.get(v - 1);
			Vector3f normal = normals.get(vn - 1);
			
			return new Vertex(position, normal);
			
		} catch(NumberFormatException | NullPointerException e) {
			
		    System.out.println("Found invalid vertex normal, model import failed.");
		    
		    return null;
		    
		}
		
	}
	
	private Vertex parseV(String vertSymbol, ArrayList<Vector3f> vertices) {
		
		try {
			
			String[] subsymbols = vertSymbol.split("//");
			
			int v = Integer.parseInt(subsymbols[0]);
			
			Vector3f position = vertices.get(v - 1);
			
			return new Vertex(position);
			
		} catch(NumberFormatException | NullPointerException e) {
			
		    System.out.println("Found invalid vertex normal, model import failed.");
		    
		    return null;
		    
		}
		
	}
	
}
