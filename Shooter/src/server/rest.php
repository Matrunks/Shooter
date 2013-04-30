<?php
	/*
	 * SHOOTER REST SCORE SERVER
	 * VERSION 1.0.0
	 *
	 * Manages a MySQL database to keep track of each player's scoring and maximum level.
	 *
	 * Currently, it does not requires registration.
	 */
	
	$mysql = mysqli_connect ("localhost", "user", "password", "database");
	
	if (mysqli_connect_errno ($mysql))
	{
		echo "Failed to connect to MySQL: " . mysqli_connect_error ();
		exit (0);
	}
	
	/* 
	 * Interpret request and execute actions accordingly.
	 * 
	 * GET: 	Returns the current top 5 players based on level reached.
	 * POST: 	If the user does not exist in the database, it creates a new row. 
	 * 			If the user exists and the new score is higher than the current score, it updates it.
	 * PUT: 	Not needed.
	 * DELETE: 	Not implemented.
	 */
	switch ($_SERVER['REQUEST_METHOD'])
	{
		case 'GET':
			$query = "SELECT name, score FROM scores ORDER BY score DESC LIMIT 5";
	
			$rows = mysqli_query ($mysql, $query);
	
			$info = array ();
			while ($row = mysqli_fetch_array ($rows, MYSQL_ASSOC))
			{
				array_push ($info, $row);
			}
	
			/* Return data econded as a JSON object. */
			echo json_encode ($info);
			break;
	
		case 'POST':		
			/* Search for the user in the DB. */
			$query = "SELECT name FROM scores WHERE name = '" . $_POST['name'] . "'";
			$result = mysqli_query ($mysql, $query);
			$row = mysqli_fetch_array ($result);
			
			if (mysqli_num_rows ($result) > 0)
			{
				/* Update existing user. */
				$query = "UPDATE scores SET score='" . $_POST['score'] . "' WHERE name = '" . $_POST['name'] . "'";
			}
			else
			{
				/* Create new user. */
				$query = "INSERT INTO scores (name, score) VALUES ('" . $_POST['name'] . "', '" . $_POST['score'] . "')";
			}
			
			mysqli_query ($mysql, $query);
						
			header ("HTTP/1.0 200 OK");
			break;
	
		case 'PUT':
			header ("HTTP/1.0 501 Not Implemented");
			break;
	
		case 'DELETE':
			header ("HTTP/1.0 501 Not Implemented");
			break;
			
		default:
			/* Well, fuck you too. */
			header ("HTTP/1.0 418 I'm a teapot");
	}
?>