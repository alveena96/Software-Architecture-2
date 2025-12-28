# Clinic MVC Application

Minimal Java Swing MVC app for managing:
- Patients
- Clinicians
- Facilities
- Staff
- Appointments
- Prescriptions (export TXT)
- Referrals (export letter TXT)

## Run your IDE
1. Install JDK 17+
2. Open folder in the IDE
3. Run `src/app/Main.java`

## Persistence
- CSV lives in `/data`
- Add/Edit/Delete auto-saves to CSV
- Each tab includes **Refresh** to reload from disk

## Exports
- `/output/prescriptions`
- `/output/referrals`

## Structure (simple MVC)
- `src/domain` Models
- `src/controller` Controllers
- `src/ui` Views (Swing)
- `src/persistence` CSV + Singleton Repository
