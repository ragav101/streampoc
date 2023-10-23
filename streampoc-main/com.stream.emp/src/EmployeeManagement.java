import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class EmployeeManagement {

    static List<EmployeeDTO> employees = new ArrayList<>();
    static {
            employees.add(
                new EmployeeDTO(1,"Shabbir", "Dawoodi", 7000.0, List.of("Project 3","Project 2"))
        );

        employees.add(
                new EmployeeDTO(2,"Nikhil", "Gupta", 9000.0, List.of("Project 1","Project 3"))
        );

        employees.add(
                new EmployeeDTO(3, "Shivam", "Kumar", 8500.0, List.of("Project 3", "Project 4"))
        );

        employees.add(
                new EmployeeDTO(4, "Anbu", "selvan", 11000.0, List.of("Project 3", "Project 5"))
        );

        employees.add(
                new EmployeeDTO(5, "Bala", "kumar", 11000.0, List.of("Project 3", "Project 5"))
        );
        }

        public static double increaseSalary(double currentSalary, double increasePercent) {
             return currentSalary + (currentSalary * (increasePercent / 100));
        }
    public static void main(String[] args) throws Exception {
        
        // iterate employee using stream
        System.out.println("\nEmployees : ");
        employees.stream().forEach(m -> System.out.println(m.toString()));


        // increase salary everyone by 30 percent
        // New Salary = Original Salary + (Original Salary * (Percent Increase / 100))
        Set<EmployeeDTO> increasedSalary = 
                                    employees.stream()
                                    .map(m -> new EmployeeDTO(
                                        m.getId(), 
                                        m.getFirstName(), 
                                        m.getLastName(), 
                                        increaseSalary(m.getSalary(), 30), 
                                        m.getProjects()))
                        .collect(Collectors.toSet());
        System.out.println("\nIncreasedSalary : ");
        increasedSalary.stream().forEach(m -> System.out.println(m.toString()));

        // fetch salary greater than 10,000
        List<EmployeeDTO> salaryGrtrThanTenK = 
                                    increasedSalary.stream()
                                    .filter(e -> e.getSalary()>10000)
                        .collect(Collectors.toList());
        
        System.out.println("\nSalary greater than ten K : ");
        salaryGrtrThanTenK.stream().forEach(m -> System.out.println(m.toString()));
        
        // fetch the first employee greater than 10,000
        EmployeeDTO findFirstEmp = increasedSalary.stream()
                                    .filter(e -> e.getSalary()>10000)
                                    .findFirst()
                .orElse(null);
                                    
        System.out.println("\nFirst employee greater than 10,000 : \n" + findFirstEmp);

        // fetch every projects of employee Stream<List<String>> convert it into String by join (",") by distinct

        String employeePresentProjects = employees.stream()
                                    .map(m -> m.getProjects())
                                    .flatMap(projects -> projects.stream())
                                    .distinct()
                                    .sorted()
                .collect(Collectors.joining(","));
                                    
        System.out.println("\nEmployes presented projects : \n" + employeePresentProjects);
        
        // short Circuit operations
        List<EmployeeDTO> shortCircuit =
                employees
                .stream()
                .skip(1)
                .limit(1)
                .collect(Collectors.toList());
        System.out.println(shortCircuit);

        // paginate the employeelist each page hold 2 employees as limit (Using skip & limit)
        int pageLimit = 2;
        int totPages = (int) Math.ceil((double) employees.size() / pageLimit);
        List<Integer> pages = IntStream.range(0, totPages)
                               .boxed()
                               .collect(Collectors.toList());
        System.out.println("\nTot emp : " + employees.size());
        pages.forEach(i -> {
            List<EmployeeDTO> pageWiseEmployees = employees
                    .stream().skip(i * pageLimit)
                    .limit(pageLimit)
                    .collect(Collectors.toList());
            System.out.println("\npage : " + (i+1));
            System.out.println("Limit : " + pageLimit);
            pageWiseEmployees.forEach(e -> System.out.println(e));
        }
        );

        // Finite Data
        System.out.println("\nFinite Data : ");
        Stream.generate(Math::random)
                .limit(5)
                .forEach(value -> System.out.println(value));
        
        // using generate stream generate random 5 OTP's
        System.out.println("\ngenerate random 5 OTP's : ");
        Stream.generate(Math::random)
                .mapToLong(m -> (long) (m * 900000 + 100000))
                .limit(6).forEach(otp -> {
                    System.out.println("OTP : " + otp);
                });
                    
        // sorting by (o1, o2) lambda type
        // ASC simple
        System.out.println("\nsimple sorting : ");
        List<EmployeeDTO> ascSortedSimple = 
                employees
                .stream()
                        .sorted((o1, o2) -> o1.getFirstName().compareToIgnoreCase(o2.getFirstName()))
                                        .collect(Collectors.toList());
        System.out.println("Ascending : ");
        ascSortedSimple.forEach(e -> System.out.println(e.toString()));

        List<EmployeeDTO> descSortedSimple = 
                employees
                .stream()
                        .sorted((o1, o2) -> o2.getFirstName().compareToIgnoreCase(o1.getFirstName()))
                                        .collect(Collectors.toList());
        System.out.println("Descending : ");
        descSortedSimple.forEach(e -> System.out.println(e.toString()));

        // Asc
        System.out.println("\n Intermediate sorting : ");
        List<EmployeeDTO> ascSortedEmployees =
                employees
                .stream()
                        .sorted((o1, o2) -> {
                            String o1Name = o1.getFirstName() + " " + o1.getLastName();
                            String o2Name = o2.getFirstName() + " " + o2.getLastName();
                            return o1Name.compareToIgnoreCase(o2Name);
                })
                        .collect(Collectors.toList());
        System.out.println("\nsorted asc using employee firstname and lastname : ");
        ascSortedEmployees.forEach(e -> System.out.println(e.toString()));

        // Desc
        List<EmployeeDTO> descSortedEmployees =
                employees
                .stream()
                        .sorted((o1, o2) -> {
                            String o1Name = o1.getFirstName() + " " + o1.getLastName();
                            String o2Name = o2.getFirstName() + " " + o2.getLastName();
                            return o2Name.compareToIgnoreCase(o1Name);
                })
                        .collect(Collectors.toList());
        System.out.println("\nsorted desc using employee firstname and lastname : ");
        descSortedEmployees.forEach(e -> System.out.println(e.toString()));

        // sorting by Comparator type
        // asc
        System.out.println("Sorting type two using Comparator");
        List<EmployeeDTO> sortedAscEmployees = employees
        .stream()
        .sorted(Comparator.comparing(
            (EmployeeDTO employee) -> employee.getFirstName() + " " + employee.getLastName()
        )).collect(Collectors.toList());

        System.out.println("\nsorted asc using employee firstname and lastname : ");
        sortedAscEmployees.forEach(e -> System.out.println(e.toString()));

        // desc
        List<EmployeeDTO> sortedDescEmployees = employees
        .stream()
        .sorted(Comparator.comparing(
            (EmployeeDTO employee) -> employee.getFirstName() + " " + employee.getLastName()
                ).reversed()).collect(Collectors.toList());
        
        System.out.println("\nsorted desc using employee firstname and lastname : ");
        sortedDescEmployees.forEach(e -> System.out.println(e.toString()));

        //Find minimum & maximum paying employee
        // Simple
        System.out.println("");
        EmployeeDTO maxPayingEmployee = increasedSalary
                .stream()
                .max(Comparator.comparing(EmployeeDTO::getSalary))
                .orElseThrow();
        
        System.out.println("\nmax Paying Employee : " + maxPayingEmployee);
        
        // intermediate
        System.out.println("\nmax Paying Employee (Intermediate): ");
        OptionalDouble maxsalary = increasedSalary.stream()
                        .mapToDouble(EmployeeDTO::getSalary)
                        .max();
        if (maxsalary.isPresent()) {
                List<EmployeeDTO> highSalaryEmp = increasedSalary.stream()
                                .filter(m -> m.getSalary() == maxsalary.getAsDouble()).collect(Collectors.toList());
                highSalaryEmp.stream().forEach(e -> System.out.println(e.toString()));
        } else {
                System.out.println("No employees in the list.");
        }

        // min
        // Simple
        EmployeeDTO minPayingEmployee = increasedSalary
                .stream()
                .min(Comparator.comparing(EmployeeDTO::getSalary))
                .orElseThrow(NoSuchElementException::new);
        
        System.out.println("\nmin Paying Employee : " + minPayingEmployee);

        // intermediate min
        System.out.println("\nmin Paying Employee (Intermediate): ");
        OptionalDouble minSalary = increasedSalary.stream()
                        .mapToDouble(EmployeeDTO::getSalary)
                        .max();
        
        if (minSalary.isPresent()) {
                List<EmployeeDTO> lowSalaryEmp = increasedSalary.stream()
                                .filter(e -> e.getSalary()==minSalary.getAsDouble())
                                .collect(Collectors.toList());
                lowSalaryEmp.stream().forEach(e -> System.out.println(e.toString()));                
        } else {
                System.out.println("No employees in the list.");
        }                               

        // Find every employee total salary using reduce
        double employeeTotSalary = increasedSalary.stream()
                                     .map(m -> m.getSalary())
                .reduce(0.0, Double::sum);
        System.out.println("\nTotal employee's salary : " + employeeTotSalary);
        
        //  Using any match find any employee present in completed projects
        System.out.println("\nEmployee present in completed projects : ");
        List<String> completedProjects = Arrays.asList("Project 2", "Project 4");
        employees.stream().forEach(e -> {
            boolean is_employee = e.getProjects().stream().anyMatch(c -> completedProjects.contains(c));
            System.out.println("\n" + e.toString());
            System.out.println("Employee in completed projects : "+is_employee);
        });
    }
}
