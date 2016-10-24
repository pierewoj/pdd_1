import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class MinhashGen {

	public int[][] M;

	public int prime[] = new int[100];

	public int hash(int i, int n, int maxVal) {
		long mult = (new Long(prime[i])) * new Long(n);
		return (int) (mult % new Long(maxVal));
	}

	public MinhashGen() {
		prime = new int[] { 103511, 103529, 103549, 103553, 103561, 103567,
				103573, 103577, 103583, 103591, 103613, 103619, 103643, 103651,
				103657, 103669, 103681, 103687, 103699, 103703, 103723, 103769,
				103787, 103801, 103811, 103813, 103837, 103841, 103843, 103867,
				103889, 103903, 103913, 103919, 103951, 103963, 103967, 103969,
				103979, 103981, 103991, 103993, 103997, 104003, 104009, 104021,
				104033, 104047, 104053, 104059, 104087, 104089, 104107, 104113,
				104119, 104123, 104147, 104149, 104161, 104173, 104179, 104183,
				104207, 104231, 104233, 104239, 104243, 104281, 104287, 104297,
				104309, 104311, 104323, 104327, 104347, 104369, 104381, 104383,
				104393, 104399, 104417, 104459, 104471, 104473, 104479, 104491,
				104513, 104527, 104537, 104543, 104549, 104551, 104561, 104579,
				104593, 104597, 104623, 104639, 104651, 104659, 104677, 104681,
				104683, 104693, 104701, 104707, 104711, 104717, 104723, 104729 };
	}

	public void printSig(int k) {
		if(k >= M[0].length)
			return;
		for (int i = 0; i < 100; i++) {
			System.out.print(M[i][k] + " ");
		}
		System.out.print("\n\n\n");
	}

	public void GenereteMinhashes(InputStream input, OutputStream output)
			throws FileNotFoundException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(input));
		String line = br.readLine();
		int numCols = Integer.parseInt(line);
		M = new int[100][numCols];
		System.out.println("M = "+100+"x"+numCols);
		for (int i = 0; i < 100; i++)
			for (int j = 0; j < numCols; j++)
				M[i][j] = Integer.MAX_VALUE;

		while ((line = br.readLine()) != null) {
			String[] splitted = line.split(" ");
			List<Integer> cols = new ArrayList<Integer>();
			int numRow = Integer.parseInt(splitted[0]);
			for (int i = 1; i < splitted.length; i++)
				cols.add(Integer.parseInt(splitted[i]));

			// computing h_i[row]
			int hashRow[] = new int[100];
			for (int i = 0; i < 100; i++)
				hashRow[i] = hash(i, numRow, numCols);

			for (int col : cols) {
				for (int i = 0; i < 100; i++) {
					if (hashRow[i] < M[i][col])
						M[i][col] = hashRow[i];
				}
			}
		}
		
		ObjectOutputStream oos = new ObjectOutputStream(output);
		oos.writeObject(M);
		oos.close();

	}
}
