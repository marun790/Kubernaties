while(true) do
	echo "before"
	curl http://localhost:8080/employee/all
	echo "after"
done;
