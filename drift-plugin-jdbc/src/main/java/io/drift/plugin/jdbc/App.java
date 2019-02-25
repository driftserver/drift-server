package io.drift.plugin.jdbc;

import java.io.IOException;
import java.util.Scanner;

public class App {

	public static void main(String[] args) throws IOException {
		App app = new App();
		app.run();
	}

	private void run() throws IOException {
		DBSnapshotComponent dbSnapshotComponent = new DBSnapshotComponent();
		dbSnapshotComponent.setup();

		Scanner scanner = new Scanner(System.in);
		while (true) {
			dbSnapshotComponent.takeSnaphot();
			scanner.nextLine();
		}

	}

}
