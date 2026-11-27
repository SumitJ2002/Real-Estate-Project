# BALAJI Real Estate Consultant

A responsive property discovery site and small listing manager for BALAJI Real Estate Consultant in Thane. The React app is under `frontend/`; the Spring Boot REST API and MySQL-backed data model are under `backend/`.

## What's included

- Home page with Buy / Rent search, featured listings, consultant and MahaRERA information.
- Property listing page with type, neighbourhood and price sorting filters.
- Property details with WhatsApp enquiry and click-to-call links.
- Admin sign-in and listing create, edit, availability and delete tools.
- Public enquiry form, saved enquiries, and a protected API endpoint for the admin inbox.
- MySQL local development setup using Docker Compose.

The frontend shows sample properties if the API is not available. Replace those sample details and images with verified current listings before publishing.

## Run locally

Requirements: Node.js, Java 17+, and Maven. The default local database is H2, which runs inside the backend; Docker and a separate MySQL install are not required.

1. Set an admin password in the shell before starting the API. PowerShell: `$env:ADMIN_PASSWORD="choose-a-strong-local-password"`
2. Start the API: `cd backend; mvn spring-boot:run`
3. In another terminal, start the web app: `cd frontend; npm install; npm run dev`
4. Open the Vite URL shown in the terminal (usually `http://localhost:5173`).

The Admin link is in the website header and footer. If `ADMIN_PASSWORD` is missing, admin access stays disabled. H2 data is stored in `backend/data/` and survives restarts. The Vite dev server proxies `/api` to the local backend. For deployment with MySQL, set `DB_URL`, `DB_DRIVER=com.mysql.cj.jdbc.Driver`, `DB_USER`, and `DB_PASSWORD`, along with `ADMIN_PASSWORD`, `FRONTEND_ORIGIN`, and `VITE_API_URL`. Use different, strong database and admin credentials in production.

## API overview

- `GET /api/properties` — available properties
- `GET /api/properties/{id}` — one property
- `POST /api/enquiries` — save a customer enquiry
- `POST /api/admin/login` — exchange admin password for session key
- `GET /api/admin/properties` — all properties (admin key required)
- `POST /api/properties`, `PUT /api/properties/{id}`, `DELETE /api/properties/{id}` — manage listings (admin key required)
- `GET /api/admin/enquiries` — saved enquiries (admin key required)

## Before publishing

Replace demo properties and image URLs, confirm contact numbers and office details, use a production database and object storage for property photos, configure HTTPS and allowed frontend origin, and review the in-memory admin session setup for your hosting environment. For multi-user administration, use a persistent session store or identity provider.

## Host a client demo on Render

The root `render.yaml` Blueprint and `Dockerfile` deploy the React site and Spring Boot API together, backed by PostgreSQL. On first startup, the API adds four clearly labeled sample listings if the database is empty.

1. Push this repository to GitHub, including the deployment files.
2. In Render, create a new Blueprint instance and connect this repository and branch.
3. Set `ADMIN_PASSWORD` to a strong, unique demo password when prompted, then apply the Blueprint.
4. Open the web service's `onrender.com` URL. The same URL serves the website and `/api` endpoints.

Render's free web services spin down after 15 minutes without traffic, and the free PostgreSQL database expires after 30 days. A sleeping service can take about a minute to start again. Upgrade the service and database if the demo needs reliable always-on access or must remain available beyond the free database period.
