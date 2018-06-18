-- phpMyAdmin SQL Dump
-- version 4.5.5
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jun 18, 2018 at 10:11 PM
-- Server version: 5.7.22-log
-- PHP Version: 7.1.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `places`
--

-- --------------------------------------------------------

--
-- Table structure for table `allplaces`
--

CREATE TABLE `allplaces` (
  `place_id` int(9) NOT NULL,
  `user_id` int(9) NOT NULL,
  `name` varchar(90) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(500) COLLATE utf8_unicode_ci NOT NULL,
  `type` int(1) NOT NULL,
  `coordinates` varchar(10000) COLLATE utf8_unicode_ci NOT NULL,
  `pfimage` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=COMPACT;

--
-- Dumping data for table `allplaces`
--

INSERT INTO `allplaces` (`place_id`, `user_id`, `name`, `description`, `type`, `coordinates`, `pfimage`) VALUES
(97, 1, 'Το φαράγγι των Σερβίων', 'Το φαράγγι των Σερβίων παρουσιάζει ιδιαίτερο ενδιαφέρον επειδή τα ασβεστολιθικά βράχια που το στολίζουν, έχουν πάρει (λόγω της διάβρωσης) διάφορα ανθρωπόμορφα και ζωόμορφα σχήματα που είναι μοναδικά σε όλη την Ελλάδα.', 3, ';172.392078;40.176069;21.996969', 'cfb33852aed5a8c107320a0807c3227b'),
(98, 1, 'Το Σπήλαιο του Δράκου', 'Το σπήλαιο Δράκου αποτελεί ένα από τα θεαματικότερα σπήλαια της Ελλάδας. Είναι διανοιγμένο μεταξύ στρωμάτων Μέσο-Ανωτέρου Λιασικού Ασβεστολίθου της Πελαγονικής ζώνης. Μετά από εξερευνήσεις αποκαλύφθηκε ότι στο εσωτερικό υπάρχουν επτά υπόγειες λίμνες με ποικίλες διαστάσεις και βάθη.', 3, ';23.974518;40.505622;21.284298', '4ac56646480bbec450645e778ea391bd'),
(99, 1, 'Βελβεντός', 'Ο Βελβεντός είναι γνωστός για το Παραδοσιακό Κέντρο του. Περιδιαβαίνοντας τα σοκάκια βλέπει κανείς παλιά σπίτια με τη Μακεδονίτικη αρχιτεκτονική, όπως επίσης και το αρχοντικό Κώστα, αναπαλαιομένο κτίσμα που αποτελεί το λαογραφικό μουσείο του Βελβεντού. ', 2, ';40.260699;22.085434;40.250677;22.067667', '9d1fe37628980737cd41a451e24ea195'),
(100, 1, 'Το Σκεπασμένο ', 'Τρία χιλιόμετρα βορειοανατολικά του Βελβεντού, στα ριζά των Πιερίων βρίσκεται το Σκεπασμένο. Μια από τις ομορφότερες φυσικές περιοχές της Δυτικής Μακεδονίας, που αποτελεί πρόκληση για τους φυσιολάτρες. Ο καταρράκτης, οι μικρές λιμνούλες, τα πλατάνια, το πανύψηλο φαράγγι και τα όμορφα μονοπάτια δημιουργούν μια μαγευτική τοποθεσία.', 2, ';40.267121;22.103742;40.264190;22.098925', 'c91b07f7b4b1d33803eb9b1ac51d5cc1'),
(101, 2, 'Λίμνη των Πετρών', 'Η Λίμνη των Πετρών βρίσκεται 1 χλμ βορειοδυτικά του Δήμου Αμυνταίου και σε μικρή απόσταση από τη λίμνη Βεγορίτιδα. Η λίμνη φαίνεται πολύ ωραία από τον αρχαιολογικό χώρο των Πετρών. Η πρόσβαση (επίσκεψη) στη λίμνη είναι δυνατή από το χωριό Πέτρες. ', 3, ';2235.566158;40.725420;21.696432', 'c84145ed7b20893ee63895f9d64ef806'),
(102, 2, 'Μεγάλη Πρέσπα', 'Μοναδικό το τοπίο για τον επισκέπτη. Από τον αυχένα του βουνού που εισχωρεί στη λίμνη της Μεγάλης Πρέσπας βλέπει αριστερά το χωριό των Ψαράδων, βόρεια τη Μεγάλη Πρέσπα καταγάλανη και με διάφανα νερά και στο βάθος μια ψηλή οροσειρά, το μεγαλύτερο μέρος του χρόνου χιονισμένη και με φωλιασμένο στον μεσημβρινό θύλακα τον ιστορικό οικισμό του Αγίου Γερμανού.', 3, ';10929.247409;40.893765;21.009320', '772a529c81d0299741862836d5ffca36'),
(103, 2, 'Μικρή Πρέσπα', 'Στην Μικρή Πρέσπα σχηματίζονται δύο νησίδες ο Άγιος Αχίλλειος και το Βιδρονήσι ή Βιτρινέτσι. Στον Άγιο Αχίλλειο υπάρχει οικισμός ο οποίος είναι ένας από τους δύο οικισμούς της Ελλάδας που είναι χτισμένοι σε νησιά λιμνών (ο άλλος είναι ο οικισμός στο νησί της λίμνης των Ιωαννίνων). Το νησί ενώνεται με την απέναντι στεριά με μία πεζογέφυρα. Κοντά στο ψηλότερο σημείο του βρίσκονται τα ερείπια του μεγάλου ναού του Αγίου Αχιλλείου που χτίστηκε από τον Τσάρο Σαμουήλ στα τέλη του 10ου αιώνα μ.Χ.', 3, ';8228.043835;40.739260;21.082310', '1e394456419e3753dc7a8ea66a2e0936'),
(104, 2, 'Δρακολίμνη Σμόλικα', 'Νοτιοδυτικά της Σαμαρίνας, στους πρόποδες του Σμόλικα, συναντάμε δύο λίμνες που είναι γνωστές ως «Δρακόλιμνες». Εδώ υπάρχουν αλπικοί τρίτωνες, ενώ πιο πέρα είναι ο μεγάλος καταρράκτης του Σμόλικα, Απα Σπιντζουριάτα (δηλ. Κρεμαστό νερό), ο οποίος ρίχνει τα νερά του στη δύσβατη πυκνοδασωμένη χαράδρα της Βάλια Κίρνα, Σκοντίνα. ', 2, ';40.090363;20.909724;40.089760;20.908351', 'bc9309b751cadf67b56163a2667824f7'),
(105, 1, 'Βυζαντινό Κάστρο Σερβίων', 'Το κάστρο των Σερβίων βρίσκεται στους δυτικούς πρόποδες των Πιερίων. Είναι χτισμένο στην πιο σημαντική, οχυρή θέση, καθώς ελέγχει το πέρασμα από τη Μακεδονία στη Θεσσαλία και τη Νότια Ελλάδα μέσω των στενών του Σαρανταπόρου. Το Κάστρο των Σερβίων κτίστηκε κάπου μεταξύ 560 και 650, δηλαδή στα χρόνια του Ιουστινιανού ή του Ηρακλείου.', 3, ';44.230308;40.176073;22.000999', '515260022af3fca4cf71740fb3347f3a'),
(106, 1, 'Καμπαναριό Αγ. Νικολάου - Ρολόι', 'Το μεγάλο ρολόι τεσσάρων όψεων που βρίσκεται στον έβδομο όροφο του καμπαναριού του Αγίου Νικολάου στην κεντρική πλατεία της πόλης. Σήμα κατατεθέν της πόλης της Κοζάνης, διαχρονικό της σύμβολο και σημείο αναφοράς και συνάντησης κατοίκων και επισκεπτών.', 2, ';40.300949;21.788070;40.300905;21.788004', 'd30c81cffa02095d819f69a812032480'),
(107, 1, 'Ιστορικό-Λαογραφικό & Φυσικής Ιστορίας Μουσείο', 'Ιδρύθηκε το 1969 και εγκαινιάσθηκε επίσημα στις 10 Οκτωβρίου 1987. Περιλαμβάνει τις εξής θεματικές κατηγορίες: Έκθεση Φυσικής Ιστορίας (από την Παλαιολιθική εποχή μέχρι και τους νεότερους χρόνους), Αρχαιολογική- Βυζαντινή έκθεση (από το 7000 π.Χ. έως 1453 μ.Χ.), Ιστορική έκθεση (από το 1453 έως 1944), Λαογραφική έκθεση, που θεωρείται και η σημαντικότερη, (από το 1640 έως 1960), κλπ', 2, ';40.301489;21.785570;40.301218;21.785068', '12cd7e0223d0a713887b298cde1bb11d'),
(108, 1, 'Αρχαιολογικό Μουσείο Αιανής', 'Το Αρχαιολογικό Μουσείο της Αιανής διαθέτει ανεκτίμητα αρχαιολογικά ευρήματα που βρέθηκαν στην αρχαία πόλη Αιανή. Βρίσκεται σε ένα μεγάλο σύγχρονο κτίριο, που χτίστηκε το 1992-2002. Το αρχαιολογικό συγκρότημα της αρχαίας πόλης βρίσκεται περίπου 2 χλμ βορειοανατολικά του χωριού και σε αυτό μπορείτε να δείτε κατάλοιπα των διαφόρων κτιρίων της αρχαίας πόλης.', 3, ';17.177634;40.167683;21.826108', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `tours`
--

CREATE TABLE `tours` (
  `tour_id` int(9) NOT NULL,
  `user_id` int(9) NOT NULL,
  `tour_name` varchar(90) COLLATE utf8_unicode_ci NOT NULL,
  `tour_places` varchar(500) COLLATE utf8_unicode_ci NOT NULL,
  `tour_desc` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=COMPACT;

--
-- Dumping data for table `tours`
--

INSERT INTO `tours` (`tour_id`, `user_id`, `tour_name`, `tour_places`, `tour_desc`) VALUES
(53, 1, 'Μουσεία', ';107;108', 'Μία περιήγηση στα άκρως ενδιαφέροντα μουσεία της Δυτικής Μακεδονίας.'),
(54, 2, 'Λίμνες Δυτ.Μακεδονία', ';101;102;103;104', 'Η Δυτική Μακεδονία έχει πολλές λίμνες, άλλες περισσότερο και άλλες λιγότερο γνωστές. Αυτή είναι μια περιήγηση στις ομορφότερες.'),
(55, 1, 'Αξιοθέατα Βελβεντού', ';99;100', 'Αν βρίσκεστε στην περιοχή του Βελβεντού, αυτά είναι τα αξιοθέατα που πρέπει να επισκεφτείτε.');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` int(9) NOT NULL,
  `username` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `password` char(60) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `surname` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `address` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `city` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `phone` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `tou` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `username`, `password`, `name`, `surname`, `email`, `address`, `city`, `phone`, `tou`) VALUES
(1, 'admin', '$2y$10$PFvmF7sRnXAUDR1lOqjA0uLacz762L87KjNZeauXBRoia3Q0.oCEC', 'lena', 'regou', 'st0344@icte.uowm.gr', 'Nav 18', 'Kozani', '6944949949', 1),
(2, 'lena', '$2y$10$MY1T9kO.SJVb8piwM8A9jOpILXAYyLD5wVsj30WWEGDbulpeOAOVC', 'lena', 'reg', 'lena@gmail.gr', '11is Oct', 'Kozani', '6999900949', 2),
(3, 'maria', '$2y$10$FRD/ocd/nN8Au3JRgk2pYuTtPjhaHPwew3.SIFCRCvgsbXXJlib0e', 'mary', 'mariou', 'maria@yahoo.com', 'Kozia 2', 'Kozani', '2105549999', 2),
(4, 'kiki', '$2y$10$kO2d9eRpzGIu6Lxcyaz55ekN.m3DyA4IXPBOKK4JIIbRPYIoerAqi', 'kiko', 'kikou', 'kik@gmail.com', 'AR 20 19200', 'Elef', '2105549999', 2),
(7, 'luthienaa', '$2y$10$sf7Vh4G3sG.uszn1uo1/JOsywmoK58T1G0yWu0V2QJP25g/j5yvHq', 'luthi', 'Lego', 'lenareg@hotmail.com', 'Aso 19', 'Olop', '5549896333', 2),
(12, 'billisss', '$2y$10$UD2.iX0L1j51qXtixT.OFey9Cebml9u9Cm0bTY/kOmi.LZstCOv5K', 'bill', 'billios', 'bil@billl.com', '', '', '', 2);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `allplaces`
--
ALTER TABLE `allplaces`
  ADD PRIMARY KEY (`place_id`);

--
-- Indexes for table `tours`
--
ALTER TABLE `tours`
  ADD PRIMARY KEY (`tour_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `allplaces`
--
ALTER TABLE `allplaces`
  MODIFY `place_id` int(9) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=109;
--
-- AUTO_INCREMENT for table `tours`
--
ALTER TABLE `tours`
  MODIFY `tour_id` int(9) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=56;
--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(9) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
