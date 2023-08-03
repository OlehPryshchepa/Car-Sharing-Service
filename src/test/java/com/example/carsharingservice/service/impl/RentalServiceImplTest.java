package com.example.carsharingservice.service.impl;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RentalServiceImplTest {

    @Mock
    private RentalRepository rentalRepository;

    @Mock
    private CarRepository carRepository;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private RentalServiceImpl rentalService;

    @Test
    void testSaveRental() {
        // Підготовка тестових даних
        Car car = new Car();
        car.setId(1L);
        car.setInventory(5);

        Rental rental = new Rental();
        rental.setId(1L);
        rental.setCar(car);
        rental.setUser(new User());
        rental.setStartDate(LocalDateTime.now());

        // Вказуємо поведінку підроблених об'єктів при виклику методів
        when(carRepository.findById(anyLong())).thenReturn(Optional.of(car));
        when(carRepository.save(any())).thenReturn(car);
        when(rentalRepository.save(any())).thenReturn(rental);

        // Виклик методу, який будемо тестувати
        Rental savedRental = rentalService.save(rental);

        // Перевірка результату
        assertNotNull(savedRental);
        assertEquals(4, car.getInventory()); // Перевірка зменшення інвентаря авто на 1

        // Перевірка, що метод sendTelegramMessage() був викликаний один раз з правильними аргументами
        verify(notificationService, times(1)).sendTelegramMessage(rental.getUser(), "Notification message");
    }

    @Test
    void testFindById() {
        // Підготовка тестових даних
        Long rentalId = 1L;
        Rental rental = new Rental();
        rental.setId(rentalId);

        // Вказуємо поведінку підробленого об'єкта RentalRepository
        when(rentalRepository.findById(rentalId)).thenReturn(Optional.of(rental));

        // Виклик методу, який будемо тестувати
        Rental foundRental = rentalService.find(rentalId);

        // Перевірка результату
        assertNotNull(foundRental);
        assertEquals(rentalId, foundRental.getId());

        // Перевірка, що метод findById() був викликаний один раз з правильним аргументом
        verify(rentalRepository, times(1)).findById(rentalId);
    }

    @Test
    void testFindByUserId() {
        // Підготовка тестових даних
        Long userId = 1L;
        boolean isActive = true;
        int page = 0;
        int size = 10;
        PageRequest request = PageRequest.of(page, size);

        List<Rental> activeRentals = Arrays.asList(new Rental(), new Rental(), new Rental());

        // Вказуємо поведінку підробленого об'єкта RentalRepository
        when(rentalRepository.findByUserIdAndActualReturnDateIsNull(userId, request))
                .thenReturn(new PageImpl<>(activeRentals));

        // Виклик методу, який будемо тестувати
        List<Rental> foundRentals = rentalService.findByUSerId(userId, isActive, request);

        // Перевірка результату
        assertNotNull(foundRentals);
        assertEquals(activeRentals.size(), foundRentals.size());

        // Перевірка, що метод findByUserIdAndActualReturnDateIsNull() був викликаний один раз з правильними аргументами
        verify(rentalRepository, times(1)).findByUserIdAndActualReturnDateIsNull(userId, request);
    }

    @Test
    void testReturnCar() {
        // Підготовка тестових даних
        Long rentalId = 1L;
        Rental rental = new Rental();
        rental.setId(rentalId);
        Car car = new Car();
        car.setInventory(4);

        // Вказуємо поведінку підроблених об'єктів RentalRepository та CarRepository
        when(rentalRepository.findById(rentalId)).thenReturn(Optional.of(rental));
        when(carRepository.save(any())).thenReturn(car);
        when(rentalRepository.save(any())).thenReturn(rental);

        // Виклик методу, який будемо тестувати
        Rental returnedRental = rentalService.returnCar(rentalId);

        // Перевірка результату
        assertNotNull(returnedRental);
        assertNotNull(returnedRental.getActualReturnDate());
        assertEquals(5, car.getInventory()); // Перевірка збільшення інвентаря авто на 1

        // Перевірка, що метод findById() був викликаний один раз з правильним аргументом
        verify(rentalRepository, times(1)).findById(rentalId);
        // Перевірка, що метод save() був викликаний один раз з правильним аргументом
        verify(carRepository, times(1)).save(any());
        // Перевірка, що метод save() був викликаний один раз з правильним аргументом
        verify(rentalRepository, times(1)).save(any());
    }

    // Додайте тести для інших методів, які потрібно перевірити.
    // Зверніть увагу, що вам можуть знадобитися додаткові підроблені об'єкти та інші підготовки перед тестами.
}