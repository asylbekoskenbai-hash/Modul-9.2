import java.util.*;

public class Main {

    //ЧАСТЬ 1: ПАТТЕРН «ФАСАД» (ГОСТИНИЦА)

    static class RoomBookingSystem {
        private Map<String, Boolean> rooms = new HashMap<>();

        public RoomBookingSystem() {
            rooms.put("Стандарт", true);
            rooms.put("Люкс", true);
            rooms.put("Президентский", true);
        }

        public boolean checkAvailability(String roomType) {
            return rooms.getOrDefault(roomType, false);
        }

        public void bookRoom(String roomType, String guestName) {
            if (checkAvailability(roomType)) {
                rooms.put(roomType, false);
                System.out.println("✅ Номер \"" + roomType + "\" забронирован для " + guestName);
            } else {
                System.out.println("❌ Номер \"" + roomType + "\" недоступен");
            }
        }

        public void cancelBooking(String roomType) {
            rooms.put(roomType, true);
            System.out.println("❌ Бронирование номера \"" + roomType + "\" отменено");
        }
    }

    static class RestaurantSystem {
        private Map<String, Boolean> tables = new HashMap<>();

        public RestaurantSystem() {
            tables.put("У окна", true);
            tables.put("В зале", true);
            tables.put("VIP-зона", true);
        }

        public void bookTable(String tableType, String guestName) {
            if (tables.getOrDefault(tableType, false)) {
                tables.put(tableType, false);
                System.out.println("🍽 Стол \"" + tableType + "\" забронирован для " + guestName);
            } else {
                System.out.println("❌ Стол \"" + tableType + "\" недоступен");
            }
        }

        public void orderFood(String guestName, String dish) {
            System.out.println("🍕 Заказ еды для " + guestName + ": " + dish);
        }

        public void cancelTableBooking(String tableType) {
            tables.put(tableType, true);
            System.out.println("❌ Бронирование стола \"" + tableType + "\" отменено");
        }
    }

    static class EventManagementSystem {
        private Map<String, Boolean> halls = new HashMap<>();
        private List<String> equipment = new ArrayList<>();

        public EventManagementSystem() {
            halls.put("Конференц-зал A", true);
            halls.put("Конференц-зал B", true);
            halls.put("Банкетный зал", true);
        }

        public void bookHall(String hallName, String eventName) {
            if (halls.getOrDefault(hallName, false)) {
                halls.put(hallName, false);
                System.out.println("🏛 Зал \"" + hallName + "\" забронирован для мероприятия: " + eventName);
            } else {
                System.out.println("❌ Зал \"" + hallName + "\" недоступен");
            }
        }

        public void orderEquipment(String equipmentName) {
            equipment.add(equipmentName);
            System.out.println("🖥 Оборудование заказано: " + equipmentName);
        }

        public void cancelEvent(String hallName) {
            halls.put(hallName, true);
            System.out.println("❌ Мероприятие в зале \"" + hallName + "\" отменено");
        }
    }

    static class CleaningService {
        private List<String> cleaningSchedule = new ArrayList<>();

        public void scheduleCleaning(String roomNumber, String time) {
            cleaningSchedule.add(roomNumber + " в " + time);
            System.out.println("🧹 Уборка номера \"" + roomNumber + "\" запланирована на " + time);
        }

        public void performCleaning(String roomNumber) {
            System.out.println("✨ Уборка номера \"" + roomNumber + "\" выполнена");
        }

        public void requestImmediateCleaning(String roomNumber) {
            System.out.println("🚨 Срочная уборка номера \"" + roomNumber + "\" запрошена и выполнена");
        }
    }

    static class TaxiService {
        public void orderTaxi(String address, String guestName) {
            System.out.println("🚕 Такси заказано для " + guestName + " по адресу: " + address);
        }
    }

    static class HotelFacade {
        private RoomBookingSystem roomBooking;
        private RestaurantSystem restaurant;
        private EventManagementSystem eventManagement;
        private CleaningService cleaning;
        private TaxiService taxi;

        public HotelFacade() {
            this.roomBooking = new RoomBookingSystem();
            this.restaurant = new RestaurantSystem();
            this.eventManagement = new EventManagementSystem();
            this.cleaning = new CleaningService();
            this.taxi = new TaxiService();
        }

        public void bookRoomWithServices(String guestName, String roomType, String dish, String cleaningTime) {
            System.out.println("\n🏨 === Бронирование номера с услугами для " + guestName + " ===");
            roomBooking.bookRoom(roomType, guestName);
            restaurant.orderFood(guestName, dish);
            cleaning.scheduleCleaning(roomType, cleaningTime);
        }

        public void organizeEventWithRooms(String eventName, String hallName, String equipment,
                                           List<String> participants, String roomType) {
            System.out.println("\n🎉 === Организация мероприятия: " + eventName + " ===");
            eventManagement.bookHall(hallName, eventName);
            eventManagement.orderEquipment(equipment);
            for (String participant : participants) {
                roomBooking.bookRoom(roomType, participant);
            }
        }

        public void bookTableWithTaxi(String guestName, String tableType, String dish, String address) {
            System.out.println("\n🍽 === Бронирование стола с такси для " + guestName + " ===");
            restaurant.bookTable(tableType, guestName);
            restaurant.orderFood(guestName, dish);
            taxi.orderTaxi(address, guestName);
        }

        public void cancelRoomBooking(String roomType) {
            roomBooking.cancelBooking(roomType);
        }

        public void requestImmediateCleaning(String roomNumber) {
            cleaning.requestImmediateCleaning(roomNumber);
        }

        public void cancelTableBooking(String tableType) {
            restaurant.cancelTableBooking(tableType);
        }
    }

    //ЧАСТЬ 2: ПАТТЕРН «КОМПОНОВЩИК» (КОРПОРАТИВНАЯ СТРУКТУРА)

    static abstract class OrganizationComponent {
        protected String name;

        public OrganizationComponent(String name) {
            this.name = name;
        }

        public abstract void display(String indent);
        public abstract double getBudget();
        public abstract int getTotalEmployees();

        public void add(OrganizationComponent component) {
            throw new UnsupportedOperationException();
        }

        public void remove(OrganizationComponent component) {
            throw new UnsupportedOperationException();
        }

        public OrganizationComponent findEmployeeByName(String name) {
            return null;
        }

        public void updateSalary(double newSalary) {
            // По умолчанию ничего не делаем
        }

        public List<OrganizationComponent> getAllEmployees() {
            return new ArrayList<>();
        }
    }

    static class Employee extends OrganizationComponent {
        private String position;
        private double salary;
        private boolean isContractor; // true - временный сотрудник (не входит в бюджет)

        public Employee(String name, String position, double salary) {
            super(name);
            this.position = position;
            this.salary = salary;
            this.isContractor = false;
        }

        public Employee(String name, String position, double salary, boolean isContractor) {
            super(name);
            this.position = position;
            this.salary = salary;
            this.isContractor = isContractor;
        }

        @Override
        public void display(String indent) {
            String contractorMark = isContractor ? " (контрактор)" : "";
            System.out.println(indent + "👤 " + name + " - " + position + " - $" + salary + contractorMark);
        }

        @Override
        public double getBudget() {
            return isContractor ? 0 : salary;
        }

        @Override
        public int getTotalEmployees() {
            return 1;
        }

        @Override
        public OrganizationComponent findEmployeeByName(String searchName) {
            if (this.name.equalsIgnoreCase(searchName)) {
                return this;
            }
            return null;
        }

        @Override
        public void updateSalary(double newSalary) {
            this.salary = newSalary;
            System.out.println("💰 Зарплата сотрудника " + name + " изменена на $" + salary);
        }

        @Override
        public List<OrganizationComponent> getAllEmployees() {
            List<OrganizationComponent> list = new ArrayList<>();
            list.add(this);
            return list;
        }

        public String getPosition() { return position; }
        public double getSalary() { return salary; }
        public boolean isContractor() { return isContractor; }
    }

    static class Department extends OrganizationComponent {
        private List<OrganizationComponent> children = new ArrayList<>();

        public Department(String name) {
            super(name);
        }

        @Override
        public void add(OrganizationComponent component) {
            if (component != null && !children.contains(component)) {
                children.add(component);
            }
        }

        @Override
        public void remove(OrganizationComponent component) {
            children.remove(component);
        }

        @Override
        public void display(String indent) {
            System.out.println(indent + "📁 " + name);
            for (OrganizationComponent child : children) {
                child.display(indent + "  ");
            }
        }

        @Override
        public double getBudget() {
            double total = 0;
            for (OrganizationComponent child : children) {
                total += child.getBudget();
            }
            return total;
        }

        @Override
        public int getTotalEmployees() {
            int total = 0;
            for (OrganizationComponent child : children) {
                total += child.getTotalEmployees();
            }
            return total;
        }

        @Override
        public OrganizationComponent findEmployeeByName(String searchName) {
            for (OrganizationComponent child : children) {
                OrganizationComponent found = child.findEmployeeByName(searchName);
                if (found != null) {
                    return found;
                }
            }
            return null;
        }

        @Override
        public List<OrganizationComponent> getAllEmployees() {
            List<OrganizationComponent> all = new ArrayList<>();
            for (OrganizationComponent child : children) {
                all.addAll(child.getAllEmployees());
            }
            return all;
        }

        public void displayAllEmployees() {
            System.out.println("\n📋 Все сотрудники отдела \"" + name + "\":");
            for (OrganizationComponent emp : getAllEmployees()) {
                System.out.println("  - " + ((Employee)emp).name + " (" + ((Employee)emp).getPosition() + ")");
            }
        }
    }

    public static void main(String[] args) {

        System.out.println("========== ДЕМОНСТРАЦИЯ ПАТТЕРНА «ФАСАД» (ГОСТИНИЦА) ==========");

        HotelFacade hotel = new HotelFacade();

        hotel.bookRoomWithServices("Иван Петров", "Люкс", "Стейк с картофелем", "10:00");

        List<String> participants = Arrays.asList("Анна Сидорова", "Олег Иванов", "Мария Козлова");
        hotel.organizeEventWithRooms("IT-Конференция", "Конференц-зал A", "Проектор + ноутбук", participants, "Стандарт");

        hotel.bookTableWithTaxi("Елена Смирнова", "У окна", "Паста Карбонара", "ул. Ленина, 10");

        hotel.requestImmediateCleaning("Люкс");
        hotel.cancelRoomBooking("Стандарт");
        hotel.cancelTableBooking("У окна");

        System.out.println("\n\n========== ДЕМОНСТРАЦИЯ ПАТТЕРНА «КОМПОНОВЩИК» (КОРПОРАТИВНАЯ СТРУКТУРА) ==========");

        Employee emp1 = new Employee("Иван Иванов", "Директор", 15000);
        Employee emp2 = new Employee("Петр Петров", "Разработчик", 5000);
        Employee emp3 = new Employee("Сидор Сидоров", "Тестировщик", 4000);
        Employee emp4 = new Employee("Анна Аннова", "Менеджер", 6000);
        Employee emp5 = new Employee("Джон Доу", "Дизайнер", 4500);
        Employee emp6 = new Employee("Майк Смит", "Разработчик", 5200, true); // Контрактор
        Employee emp7 = new Employee("Елена Еленова", "HR", 3800);

        Department headOffice = new Department("Головной офис");
        Department itDepartment = new Department("IT-отдел");
        Department qaDepartment = new Department("QA-отдел");
        Department designDepartment = new Department("Дизайн-отдел");
        Department hrDepartment = new Department("HR-отдел");

        headOffice.add(emp1);
        headOffice.add(itDepartment);
        headOffice.add(hrDepartment);

        itDepartment.add(emp2);
        itDepartment.add(emp6);
        itDepartment.add(qaDepartment);
        itDepartment.add(designDepartment);

        qaDepartment.add(emp3);
        designDepartment.add(emp5);
        hrDepartment.add(emp4);
        hrDepartment.add(emp7);

        System.out.println("=== Корпоративная структура ===");
        headOffice.display("");

        System.out.println("\n📊 Бюджет головного офиса: $" + headOffice.getBudget());
        System.out.println("👥 Общее количество сотрудников: " + headOffice.getTotalEmployees());
        System.out.println("(Контракторы не учитываются в бюджете)");

        System.out.println("\n🔍 Поиск сотрудника 'Петр Петров':");
        OrganizationComponent found = headOffice.findEmployeeByName("Петр Петров");
        if (found != null) {
            found.display("");
        }

        System.out.println("\n💰 Изменение зарплаты:");
        emp2.updateSalary(5500);
        System.out.println("Новый бюджет головного офиса: $" + headOffice.getBudget());

        itDepartment.displayAllEmployees();

        System.out.println("\n📌 Информация о контракторе:");
        emp6.display("");
        System.out.println("Вклад в бюджет отдела: $" + emp6.getBudget() + " (не включается)");
    }
}